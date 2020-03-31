package com.android.proteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.proteam.model.HealthDetailsModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QMADashBoardActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_m_a_dash_board);

        LinearLayout card1=findViewById(R.id.card1);

        LinearLayout card3=findViewById(R.id.card3);
        LinearLayout card4=findViewById(R.id.card4);
        LinearLayout card5=findViewById(R.id.card5);
        LinearLayout card6=findViewById(R.id.card6);
        LinearLayout card7=findViewById(R.id.card7);
        LinearLayout card8=findViewById(R.id.card8);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QMADashBoardActivity.this,HealthDetailsActivity.class));
            }
        });


        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                raiseAlert();
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QMADashBoardActivity.this,RaiseRequestActivity.class));
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QMADashBoardActivity.this,UploadSelfieActivity.class));
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QMADashBoardActivity.this,ReportsActivity.class));
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QMADashBoardActivity.this,SettingsActivity.class));
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SharedPrefManager.getInstance(getApplicationContext()).logout();
               startActivity(new Intent(QMADashBoardActivity.this,SplashScreenActivity.class));
               finish();
            }
        });




    }


    public void raiseAlert()
    {
        progressDialog = new ProgressDialog(QMADashBoardActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        Log.d("responseData",SharedPrefManager.getInstance(getApplicationContext()).getId()+" h");
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<HealthDetailsModel> call = service.updateHealthDetails(SharedPrefManager.getInstance(getApplicationContext()).getId(),null,null,"1");
        call.enqueue(new Callback<HealthDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<HealthDetailsModel> call, @NonNull final Response<HealthDetailsModel> response) {
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
                        if (response.body().getStatus()==200) {

                            Toast.makeText(QMADashBoardActivity.this, "Alert raised successfully", Toast.LENGTH_SHORT).show();


                        } else {

                            Toast.makeText(QMADashBoardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
