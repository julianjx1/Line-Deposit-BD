package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentTransactionLimitBinding;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.Objects;


public class TransactionLimitFragment extends Fragment implements TransactionObserver, TransactionLimitObserver {

    FragmentTransactionLimitBinding binding;
    ArrayList<String> processList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_transaction_limit, container, false);
        Constant.network.limitObserver = this;
        processList.add(PaymentType.Deposit.toString());
        processList.add(PaymentType.Withdraw.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,processList);
        binding.transactionProcess.setAdapter(adapter);
        Constant.network.transactionObserver  = this;
        binding.transactionProcess.setOnItemClickListener((parent, view, position, id) -> {
            Constant.network.getTransactionLimit(PaymentType.valueOf(processList.get(position)));
        });
        binding.btnSave.setOnClickListener(v -> {
            try{
                int amount = Integer.parseInt(Objects.requireNonNull(binding.etTransationLimit.getText()).toString());
                if (amount < 0){
                    Toast.makeText(requireContext(), "Limit must be non negative",Toast.LENGTH_SHORT).show();
                    return;
                }
                String paymentTypeValue = binding.transactionProcess.getText().toString();
                PaymentType paymentType = PaymentType.valueOf(paymentTypeValue);
                Constant.network.setTransactionLimit(paymentType, amount);

            }catch (Exception ignored){

            }
        });
        return binding.getRoot();

    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLimit(int limit) {
        binding.etTransationLimit.setText(String.valueOf(limit));
    }
}