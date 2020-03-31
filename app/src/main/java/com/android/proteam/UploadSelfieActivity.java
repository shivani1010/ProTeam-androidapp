package com.android.proteam;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.android.proteam.model.HealthDetailsModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UploadSelfieActivity extends AppCompatActivity {

    Button mCaptureBtn;
    ImageView mImageView;
    File photoFile = null;
    ProgressDialog progressDialog;
    public static final int REQUEST_PERMISSION_CODE = 13;

    public static final int RESULT_CAMERA = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_selfie);



        mImageView = findViewById(R.id.imageView2);



        mCaptureBtn = findViewById(R.id.button_click);
       Button btn_upload=findViewById(R.id.button_upload);

       btn_upload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               uploadSelfie(photoFile);
           }
       });


        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                                                           if (checkPermission())
                                                           {
                                                               openCamera();

                                                           } else {

                                                               requestPermission();

                                                           }
                                                       }
                                                   }
                                               });
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(UploadSelfieActivity.this, new String[]
                {
                        CAMERA,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE
                }, REQUEST_PERMISSION_CODE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case RESULT_CAMERA:
                    compressImageFile(photoFile, mImageView);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERMISSION_CODE:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteStoragePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (CameraPermission && ReadStoragePermission && WriteStoragePermission) {

                        openCamera();

                    } else {
                        Toast.makeText(UploadSelfieActivity.this, "Allow permissions", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
        }
    }
    private void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI =  FileProvider.getUriForFile(UploadSelfieActivity.this,getPackageName()+                                                                                                    ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,RESULT_CAMERA);
            }
        }
    }

    private boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(UploadSelfieActivity.this, CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(UploadSelfieActivity.this, READ_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(UploadSelfieActivity.this, WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        //String imageFilePath = image.getAbsolutePath();
        return image;
    }

    @SuppressLint("CheckResult")
    public void compressImageFile(File actualImage, final ImageView imageview)
    {

        new Compressor(UploadSelfieActivity.this)
                .compressToFileAsFlowable(actualImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        photoFile=file;

                        Glide.with(UploadSelfieActivity.this)
                                .load(file)
                                .into(imageview);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });

    }



    private void uploadSelfie(File image) {


        progressDialog = new ProgressDialog(UploadSelfieActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        String id= String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getId());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("selfie_img[]", image.getName(), reqFile);
        RequestBody cust_id_body = RequestBody.create(MediaType.parse("text/plain"),id);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<HealthDetailsModel> call = service.uploadSelfie(body,cust_id_body);
        call.enqueue(new Callback<HealthDetailsModel>() {
            @Override
            public void onResponse(@io.reactivex.annotations.NonNull Call<HealthDetailsModel> call, @io.reactivex.annotations.NonNull Response<HealthDetailsModel> response) {
                Log.d("responseData",new Gson().toJson(response.body())+" "+response.code());

                progressDialog.dismiss();
                switch (response.code()) {
                    case 401:

                        break;
                    case 404:

                        break;
                    case 500:

                        break;
                    case 200:
                        if(response.body().getStatus()==200)
                        {
                            Toast.makeText(UploadSelfieActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(UploadSelfieActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
            }

            @Override
            public void onFailure(Call<HealthDetailsModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

}
