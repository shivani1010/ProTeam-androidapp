package com.android.proteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.proteam.model.RegisterModel;
import com.android.proteam.model.SearchByMobileModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseRequestActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView txt_name;
    String mobile_number="", name="",city_id="",last_name=" ",middle_name=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_request);
        SearchView editSearch =findViewById(R.id.searchView);
        txt_name=findViewById(R.id.txt_name);

        Button btn_submit=findViewById(R.id.btn_submit);

        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 10) {

                searchMobile(s);
                }
                return false;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    public void searchMobile(final String mobile)
    {
        progressDialog = new ProgressDialog(RaiseRequestActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        Log.d("responseData",SharedPrefManager.getInstance(getApplicationContext()).getId()+" h");
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SearchByMobileModel> call = service.searchByMobileNo(mobile);
        call.enqueue(new Callback<SearchByMobileModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchByMobileModel> call, @NonNull final Response<SearchByMobileModel> response) {
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
                        if(null!=response.body().getUser())
                        {
                            txt_name.setText(response.body().getUser().getName()+" "+response.body().getUser().getLast_name());

                        }else{
                            Intent i=new Intent(RaiseRequestActivity.this,RegisterActivity.class);
                            i.putExtra("case","CHKUP_REQUEST");
                            startActivity(i);

                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<SearchByMobileModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    public void register()
    {
        progressDialog = new ProgressDialog(RaiseRequestActivity.this);
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

                            Toast.makeText(RaiseRequestActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(RaiseRequestActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                           finish();

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
