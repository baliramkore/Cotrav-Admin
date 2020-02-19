package com.cotrav.cotrav_admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.ui.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private     static  int SPLASH_TIME_OUT = 500;

    SharedPreferences fingerprintPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fingerprintPref = getSharedPreferences("settings_info",MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent homeIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(homeIntent);
                    finish();
            }
        },SPLASH_TIME_OUT);
    }


}
