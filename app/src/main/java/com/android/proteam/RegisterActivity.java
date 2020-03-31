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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.proteam.model.RegisterModel;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String fname="", mname="", lname="";
    String caser="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btn_submit=findViewById(R.id.btn_submit);

        EditText edt_fname=findViewById(R.id.edt_name);
        EditText edt_mname=findViewById(R.id.edt_mname);
        EditText edt_lname=findViewById(R.id.edt_lame);

        TextView txt_state=findViewById(R.id.edt_state);
        TextView txt_city=findViewById(R.id.edt_city);

        txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }

    public void register(String name,String middle_name,String last_name,String mobile_number,String city_id)
    {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        Log.d("responseData",SharedPrefManager.getInstance(getApplicationContext()).getId()+" h");
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RegisterModel> call = service.register(name,middle_name,last_name,mobile_number,city_id,"CHKUP_REQUEST");
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(@NonNull Call<RegisterModel> call, @NonNull final Response<RegisterModel> response) {
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
                        if(response.body().getError())
                        {

                            Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                            if(caser.equalsIgnoreCase("REGISTER")) {
                                SharedPrefManager.getInstance(getApplicationContext()).saveDetails(response.body().getUser().getName(),response.body().getUser().getLast_name(),response.body().getUser().getNoti_key(),response.body().getUser().getQstatus(),response.body().getUser().getStatus(),response.body().getUser().getId(),true);
                                Intent i = new Intent(RegisterActivity.this, CMADashboardActivity.class);
                                    startActivity(i);
                                    finish();

                            }else{
                                Intent i = new Intent(RegisterActivity.this, QMADashBoardActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}
