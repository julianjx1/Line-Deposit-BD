package com.line_deposit.test.bd.view.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.ActivityLoginBinding;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.MainActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginBinding.btnSignIn.setOnClickListener(l -> {


            Constant.user =  Constant.network.login(new User(loginBinding.etUserName.getText().toString(),loginBinding.etPassword.getText().toString()));

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }
}