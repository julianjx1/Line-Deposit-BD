package com.line_deposit.test.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.FragmentAddNewUserBinding;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.model.UserType;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.fragment.user.TransactionObserver;

public class AddNewUserFragment extends Fragment implements TransactionObserver {

    FragmentAddNewUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_user, container, false);
        Constant.network.transactionObserver = this;

        binding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                User user = Constant.userMap.get(binding.etUsername.getText().toString());
                if(user != null){
                    binding.etMobile.setText(user.mobile);
                    binding.etPassword.setText(user.password);
                }
                else{
                    binding.etMobile.setText("");
                    binding.etPassword.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnSave.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            String mobile = binding.etMobile.getText().toString();
            if (username.isEmpty()){
                Toast.makeText(requireContext(), "Username must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()){
                Toast.makeText(requireContext(), "password must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mobile.isEmpty()){
                Toast.makeText(requireContext(), "mobile number must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(username, password, UserType.user, mobile);
            Constant.network.addUser(user);
        });
        return binding.getRoot();
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}