package com.line_deposit.bd.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.line_deposit.bd.R;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.Authentication.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Constant.sharedPreferenceInit(this);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, (Constant.user != null) ? MainActivity.class : LoginActivity.class));
            finish();
        }, 2500);
    }
}