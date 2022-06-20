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
import com.line_deposit.bd.databinding.FragmentWithdrawBinding;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.model.TransactionProcess;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.admin.TransactionLimitObserver;
import com.line_deposit.bd.view.fragment.admin.TransactionRequestObserver;

import java.util.ArrayList;
import java.util.Objects;


public class WithdrawFragment extends Fragment implements TransactionObserver, TransactionRequestObserver, TransactionLimitObserver {

    FragmentWithdrawBinding binding;
    int limit = -1 ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_withdraw, container, false);
        Constant.network.transactionObserver = this;
        Constant.network.transactionRequestObserver  = this;
        Constant.network.limitObserver = this;
        Constant.network.getTransactionLimit(PaymentType.Withdraw);
        Constant.network.userTransactionRequest(PaymentType.Withdraw, Constant.user.username);
        ArrayList<String> type = new ArrayList<>(Constant.transactionProcessMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,type);
        binding.etMobileNumberDeposit.setText(Constant.user.mobile);
        binding.depositAccountType.setAdapter(adapter);


        binding.btnWithdraw.setOnClickListener(v -> {
            String mobileNumber = Objects.requireNonNull(binding.etMobileNumberDeposit.getText()).toString();
            String amountText = Objects.requireNonNull(binding.etDepositAmount.getText()).toString();
            int amount = 0;
            try {
                amount = Integer.parseInt(amountText);
            }catch (Exception ignored){

            }
            if (limit != -1 && amount < limit){
                Toast.makeText(requireContext(), "Minimum withdraw limit "+ limit, Toast.LENGTH_SHORT).show();
            return;
            }
            if (amount > Constant.user.balance){
                Toast.makeText(requireContext(), "You have not sufficient balance. Your balance is "+ Constant.user.balance, Toast.LENGTH_SHORT).show();
                return;
            }
            Transaction transaction = new Transaction();
            transaction.mobileNumber = mobileNumber;
            transaction.amount = amount;
            transaction.paymentType = PaymentType.Withdraw;
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        binding.etDepositAmount.setText("");
        binding.depositAccountType.setText("");
        binding.btnWithdraw.setEnabled(false);
    }

    @Override
    public void transactionRequests(ArrayList<Transaction> transactions) {
        if (binding.btnWithdraw.isEnabled()) {
            for (Transaction transaction : transactions) {
                if (transaction.username.compareTo(Constant.user.username) == 0 && transaction.paymentType == PaymentType.Withdraw) {
                    binding.btnWithdraw.setEnabled(false);
                    Toast.makeText(requireContext(), "A withdraw request has already pending. After complete, you will send a new withdraw request", Toast.LENGTH_SHORT).show();
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