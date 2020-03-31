package com.android.proteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.proteam.model.GenerateOTPModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        final AppCompatEditText editText=findViewById(R.id.editText2);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile_number=editText.getText().toString();
                if(mobile_number.isEmpty())
                {
                    editText.setError("Invalid Mobile Number");
                }else {
                    genrateOtp(mobile_number);
                }
            }
        });
    }


    public void genrateOtp(final String mobile_number)
        {
            progressDialog = new ProgressDialog(LoginActivityActivity.this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            /*Create handle for the RetrofitInstance interface*/
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<GenerateOTPModel> call = service.generateOtp(mobile_number);
            call.enqueue(new Callback<GenerateOTPModel>() {
                @Override
                public void onResponse(@NonNull Call<GenerateOTPModel> call, @NonNull final Response<GenerateOTPModel> response) {
                    Log.d("responseData",new Gson().toJson(response.body())+" "+response.code());

                    progressDialog.dismiss();
                    switch (response.code()) {
                        case 401:

                            break;
                        case 404:

                            break;
                        case 500:

                            break;
                        case 429:

                            break;
                        case 200:

                            assert response.body() != null;
                            if (response.body().getError()) {


                            } else {

                                Intent i=new Intent(LoginActivityActivity.this,VerifyOtpActivity.class);
                                i.putExtra("mobile_number",mobile_number);
                                i.putExtra("generate_otp",response.body().getOtp_code().getCode());
                                try{
                                if(null!=response.body().getUser()) {
                                    i.putExtra("name", response.body().getUser().getName());
                                    i.putExtra("last_name", response.body().getUser().getLast_name());
                                    i.putExtra("noti_key", response.body().getUser().getNoti_key());
                                    i.putExtra("Qstatus", response.body().getUser().getQstatus());
                                    i.putExtra("status", response.body().getUser().getStatus());
                                    i.putExtra("city_id", response.body().getUser().getCity_id());
                                    i.putExtra("id", response.body().getUser().getId());
                                }
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                startActivity(i);
                                finish();


                            }
                            break;
                    }
                }

                @Override
                public void onFailure(Call<GenerateOTPModel> call, Throwable t) {
                    t.printStackTrace();
                    progressDialog.dismiss();
                }
            });
        }
}
