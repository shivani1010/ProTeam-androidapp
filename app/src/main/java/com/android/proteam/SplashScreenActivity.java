package com.android.proteam;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn());
        {
            if(SharedPrefManager.getInstance(getApplicationContext()).getKeyQuarantineStatus().equalsIgnoreCase("Y"))
            {
                Intent i = new Intent(SplashScreenActivity.this, QMADashBoardActivity.class);
                startActivity(i);
                finish();

            }else if(SharedPrefManager.getInstance(getApplicationContext()).getKeyQuarantineStatus().equalsIgnoreCase("N"))
            {
                Intent i = new Intent(SplashScreenActivity.this, CMADashboardActivity.class);
                startActivity(i);
                finish();

            }
        }

        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
