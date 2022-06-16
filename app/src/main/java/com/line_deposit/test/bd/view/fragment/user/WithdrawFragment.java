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
import com.line_deposit.test.bd.view.fragment.admin.TransactionRequestObserver;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class WithdrawFragment extends Fragment implements TransactionObserver, TransactionRequestObserver {

    FragmentWithdrawBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_withdraw, container, false);
        Constant.network.transactionObserver = this;
        Constant.network.transactionRequestObserver  = this;
        Constant.network.userTransactionRequest(PaymentType.Withdraw, Constant.user.username);
        ArrayList<String> type = new ArrayList<>(Constant.transactionProcessMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,type);
        binding.etMobileNumberDeposit.setText(Constant.user.mobile);
        binding.depositAccountType.setAdapter(adapter);


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
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        binding.etDepositAmount.setText("");
        binding.etReference.setText("");
        binding.depositAccountType.setText("");
        binding.btnWithdraw.setEnabled(false);
    }

    @Override
    public void transactionRequstions(ArrayList<Transaction> transactions) {
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
}