package com.android.proteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.proteam.model.HealthDetailsModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthDetailsActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView txt_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_details);

        final EditText edt_body_temp=findViewById(R.id.edt_body_temp);
        final EditText edt_desc=findViewById(R.id.edt_symptoms);
        Button btn_submit=findViewById(R.id.btn_submit);
        txt_error=findViewById(R.id.txt_error);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String body_temp=edt_body_temp.getText().toString();
                String body_desc=edt_desc.getText().toString();
                boolean isError=false;
                if(null==body_desc||body_desc.isEmpty())
                {
                    edt_desc.setError("Field cannot be empty");
                    isError=true;
            }
                if(null==body_temp||body_temp.isEmpty())
                {
                    edt_body_temp.setError("Field cannot be empty");
                    isError=true;
                }

                if(!isError)
                {
                    updateHealthDetails(body_temp,body_desc);
                }


            }
        });


    }



    public void updateHealthDetails(final String body_temp,String desc)
    {
        progressDialog = new ProgressDialog(HealthDetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        Log.d("responseData",SharedPrefManager.getInstance(getApplicationContext()).getId()+" h");
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<HealthDetailsModel> call = service.updateHealthDetails(SharedPrefManager.getInstance(getApplicationContext()).getId(),body_temp,desc,"0");
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
                        txt_error.setVisibility(View.GONE);
                        assert response.body() != null;
                        if (response.body().getStatus()==200) {

                            Toast.makeText(HealthDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();

                        } else {

                            txt_error.setText(response.body().getMessage()+"");
                            txt_error.setVisibility(View.VISIBLE);

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
