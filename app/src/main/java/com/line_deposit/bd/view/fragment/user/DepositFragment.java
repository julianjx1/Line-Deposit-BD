package com.line_deposit.bd.view.fragment.user;


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
import com.line_deposit.bd.databinding.FragmentDepositBinding;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.model.TransactionProcess;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.admin.TransactionLimitObserver;
import com.line_deposit.bd.view.fragment.admin.TransactionRequestObserver;

import java.util.ArrayList;
import java.util.Objects;


public class DepositFragment extends Fragment implements TransactionObserver, TransactionRequestObserver, TransactionLimitObserver {
    FragmentDepositBinding binding;
    int limit = -1 ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_deposit, container, false);
        Constant.network.transactionObserver = this;
        Constant.network.transactionRequestObserver  = this;
        Constant.network.limitObserver = this;
        Constant.network.getTransactionLimit(PaymentType.Deposit);
        Constant.network.userTransactionRequest(PaymentType.Deposit, Constant.user.username);
        ArrayList<String> type = new ArrayList<>(Constant.transactionProcessMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,type);
        binding.depositAccountType.setAdapter(adapter);
        binding.etMobileNumberDeposit.setText(Constant.user.mobile);
        binding.mobileNumberLayout.setVisibility(View.GONE);
        binding.depositAccountType.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.mobileNumberLayout.setVisibility(View.VISIBLE);
            String processName = binding.depositAccountType.getText().toString();
            String number = Constant.transactionProcessMap.get(processName);

            binding.number.setText(number);
        });
        binding.btnDepositAmount.setOnClickListener(v -> {
            String mobileNumber = Objects.requireNonNull(binding.etMobileNumberDeposit.getText()).toString();
            String amountText = Objects.requireNonNull(binding.etDepositAmount.getText()).toString();
            String transactionId = Objects.requireNonNull(binding.etTransactionId.getText()).toString();
            int amount = 0;

            if(mobileNumber.isEmpty()){
                Toast.makeText(requireContext(), "Mobile number must be given "+ limit, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                amount = Integer.parseInt(amountText);
            }catch (Exception ignored){

            }
            if (limit != -1 && amount < limit){
                Toast.makeText(requireContext(), "Minimum deposit limit "+ limit, Toast.LENGTH_SHORT).show();
                return;
            }
            if (transactionId.isEmpty()){
                Toast.makeText(requireContext(), "Transaction Id must be filled up", Toast.LENGTH_SHORT).show();
                return;
            }
            Transaction transaction = new Transaction();
            transaction.mobileNumber = mobileNumber;
            transaction.amount = amount;
            transaction.transactionId = transactionId;
            transaction.paymentType = PaymentType.Deposit;
            transaction.transactionType = binding.depositAccountType.getText().toString();
            transaction.transactionProcess = TransactionProcess.Processing;
            Constant.network.transactionRequestObserver  = null;

            transaction.date = Constant.getCurrentTime();
            Constant.network.userTransaction(transaction);
        });

        return  binding.getRoot();


    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        binding.etDepositAmount.setText("");
        binding.depositAccountType.setText("");
        binding.etTransactionId.setText("");
        binding.mobileNumberLayout.setVisibility(View.GONE);
        binding.btnDepositAmount.setEnabled(false);
    }

    @Override
    public void transactionRequests(ArrayList<Transaction> transactions) {
        if (binding.btnDepositAmount.isEnabled()){
            for (Transaction transaction : transactions) {
                if (transaction.username.compareTo(Constant.user.username) == 0 && transaction.paymentType == PaymentType.Deposit) {
                    binding.btnDepositAmount.setEnabled(false);
                    Toast.makeText(requireContext(), "A deposit request has already pending. After complete, you will send a new withdraw request", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
    }
    }

    @Override
    public void getLimit(int limit) {
        this.limit = limit;
    }
}