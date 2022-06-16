package com.line_deposit.test.bd.view.fragment.user;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.FragmentWithdrawBinding;
import com.line_deposit.test.bd.model.PaymentType;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.TransactionProcess;
import com.line_deposit.test.bd.utilites.Constant;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class WithdrawFragment extends Fragment implements TransactionObserver {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentWithdrawBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_withdraw, container, false);
        Constant.network.transactionObserver = this;
        String[] type = new String[] {"BKash", "Nagad", "UPay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,type);
        binding.depositAccountType.setAdapter(adapter);
        binding.depositAccountType.setText(type[0]);
        binding.btnWithdraw.setOnClickListener(v -> {
            String mobileNumber = Objects.requireNonNull(binding.etMobileNumberDeposit.getText()).toString();
            String amountText = Objects.requireNonNull(binding.etDepositAmount.getText()).toString();
            int amount = Integer.parseInt(amountText);
            if (amount <= 0){
                Toast.makeText(requireContext(), "Amount must be greater than 0", Toast.LENGTH_SHORT).show();;
            return;
            }
            Transaction transaction = new Transaction();
            transaction.mobileNumber = mobileNumber;
            transaction.amount = amount;
            transaction.paymentType = PaymentType.Withdraw;
            transaction.transactionType = binding.depositAccountType.getText().toString();
            transaction.transactionProcess = TransactionProcess.Processing;
            transaction.date = Constant.getCurrentTime();
            Constant.network.userTransaction(transaction);
        });
        return  binding.getRoot();
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}