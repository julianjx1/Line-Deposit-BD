package com.line_deposit.bd.view.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.ActivityLoginBinding;
import com.line_deposit.bd.model.User;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.MainActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginObserver {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        Constant.network.loginObserver = this;
        loginBinding.btnLogin.setOnClickListener(l -> {
            Constant.network.login(Objects.requireNonNull(loginBinding.etUserName.getText()).toString(), Objects.requireNonNull(loginBinding.etPassword.getText()).toString());
        });
    }

    @Override
    public void authenticationStatus(Boolean success, String message, User user) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (success){
            Constant.network.loginObserver = null;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }
}