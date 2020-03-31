package com.android.proteam;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyOtpActivity extends AppCompatActivity {
    String name="",last_name="",noti_key="",Qstatus="",status="",city_id="";
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_verify_otp);

        TextView txt_mobile_number=findViewById(R.id.txt_mobile_number);
        final EditText edt_otp=findViewById(R.id.editText2);

        txt_mobile_number.setText("+91 "+getIntent().getStringExtra("mobile_number"));
        final String generate_otp=getIntent().getStringExtra("generate_otp");
        if(getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id",0);
        }
        if(getIntent().hasExtra("name")) {
              name = getIntent().getStringExtra("name");
        }
        if(getIntent().hasExtra("last_name")) {
              last_name = getIntent().getStringExtra("last_name");
        }
        if(getIntent().hasExtra("noti_key")) {
              noti_key = getIntent().getStringExtra("noti_key");
        }
        if(getIntent().hasExtra("Qstatus")) {
              Qstatus = getIntent().getStringExtra("Qstatus");
        }
        if(getIntent().hasExtra("status")) {
              status = getIntent().getStringExtra("status");
        }
        if(getIntent().hasExtra("city_id")) {
              city_id = getIntent().getStringExtra("city_id");
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp_enter=edt_otp.getText().toString();
                if(otp_enter.isEmpty())
                {
                    edt_otp.setError("Invalid OTP");
                }else {
                    if (generate_otp.equalsIgnoreCase(otp_enter)) {
                        if(Qstatus.equalsIgnoreCase("Y") ||Qstatus.equalsIgnoreCase("N"))
                        {
                            SharedPrefManager.getInstance(getApplicationContext()).saveDetails(name,last_name,noti_key,Qstatus,status,id,true);

                        }

                        if(Qstatus.equalsIgnoreCase("Y"))
                        {
                            Intent i = new Intent(VerifyOtpActivity.this, QMADashBoardActivity.class);
                            startActivity(i);
                            finish();

                        }else if(Qstatus.equalsIgnoreCase("N"))
                        {
                            Intent i = new Intent(VerifyOtpActivity.this, CMADashboardActivity.class);
                            startActivity(i);
                            finish();

                        }else{

                            Intent i = new Intent(VerifyOtpActivity.this, RegisterActivity.class);
                            startActivity(i);
                            finish();

                        }

                    }else{
                        edt_otp.setError("Invalid OTP");
                    }
                }
            }
        });
    }

}
