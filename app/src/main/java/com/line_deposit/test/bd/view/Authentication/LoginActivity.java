package com.line_deposit.test.bd.view.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.ActivityLoginBinding;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.model.UserType;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.MainActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginObserver {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        Constant.network.loginObserver = this;
        loginBinding.btnSignIn.setOnClickListener(l -> {
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