package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentAddNewUserBinding;
import com.line_deposit.bd.model.User;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;

import java.util.Objects;

public class AddNewUserFragment extends Fragment implements TransactionObserver {

    FragmentAddNewUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_user, container, false);
        Constant.network.transactionObserver = this;
        binding.labelAffiliatePercent.setVisibility(View.GONE);
        if(isAffiliate())
        {
            binding.etUsername.setHint("Affiliate username");
            binding.labelAffiliatePercent.setVisibility(View.VISIBLE);
        }
        binding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                User user = Constant.userMap.get(Objects.requireNonNull(binding.etUsername.getText()).toString());
                if(user != null){
                    binding.etMobile.setText(user.mobile);
                    binding.etPassword.setText(user.password);
                    if(user.userType == UserType.affiliate)
                    binding.etAffiliatePercent.setText(String.valueOf(user.affiliatePercent));
                }
                else{
                    binding.etMobile.setText("");
                    binding.etPassword.setText("");
                    binding.etAffiliatePercent.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnSave.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            String mobile = Objects.requireNonNull(binding.etMobile.getText()).toString();
            String affiliatePercent = Objects.requireNonNull(binding.etAffiliatePercent.getText()).toString();

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
            if (affiliatePercent.isEmpty() && isAffiliate()){
                Toast.makeText(requireContext(), "Affiliate percent must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(username, password, isAffiliate() ? UserType.affiliate : UserType.user, mobile);

            try {
                if (isAffiliate())
                    user.affiliatePercent = Integer.parseInt(affiliatePercent);
            }
            catch (NumberFormatException e){
                Toast.makeText(requireContext(), "Affiliate percent must be a number", Toast.LENGTH_SHORT).show();
            }
            Constant.network.addUser(user);
        });
        return binding.getRoot();
    }

    private Boolean isAffiliate(){
        if(getArguments()!= null){
            return getArguments().getString("userType").compareToIgnoreCase(UserType.affiliate.toString()) == 0;
        }
        return false;
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        binding.etUsername.setText("");
        binding.etPassword.setText("");
        binding.etMobile.setText("");
        binding.etAffiliatePercent.setText("");
    }
}