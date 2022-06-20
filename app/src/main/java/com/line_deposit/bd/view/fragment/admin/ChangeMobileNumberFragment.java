package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentChangeMobileNumberBinding;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;


public class ChangeMobileNumberFragment extends Fragment implements TransactionObserver {

    // TODO: Rename parameter arguments, choose names that match
    FragmentChangeMobileNumberBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_mobile_number,container,false);
        Constant.network.transactionObserver = this;
        if(Constant.user.mobile != null){
            binding.etMobileNumber.setText(Constant.user.mobile);
        }
        binding.btnSave.setOnClickListener(v -> {
            String number = binding.etMobileNumber.getText().toString();
            if(number.isEmpty()) {
                Toast.makeText(requireContext(), "Phone number must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            Constant.user.mobile = number;
            Constant.network.addUser(Constant.user);
        });
        return binding.getRoot();
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}