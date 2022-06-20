package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentRequestTransactionBinding;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.model.TransactionProcess;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.adapter.RequestTransactionObserver;
import com.line_deposit.bd.view.adapter.RequestTransactionsAdapter;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.Collections;


public class RequestTransactionFragment extends Fragment implements TransactionObserver, TransactionRequestObserver, RequestTransactionObserver {

    FragmentRequestTransactionBinding binding;
    private RequestTransactionsAdapter adapter;
    private Transaction updateTransaction;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(
                 inflater, R.layout.fragment_request_transaction, container, false);
        assert getArguments() != null;
        if (getArguments().containsKey("request_type"))
        Constant.network.userTransactionRequest(PaymentType.valueOf(getArguments().getString("request_type")));
        Constant.network.transactionRequestObserver = this;
        Constant.network.transactionObserver = this;
        return binding.getRoot();
    }

    @Override
    public void transactionRequests(ArrayList<Transaction> transactions) {
        Collections.reverse(transactions);
        adapter = new RequestTransactionsAdapter(transactions, Constant.userMap, requireContext(), this);
        binding.requestTransactionList.setHasFixedSize(true);
        binding.requestTransactionList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.requestTransactionList.setAdapter(adapter);
        Constant.network.transactionRequestObserver = null;
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        if (success){
            adapter.updateAdapter(updateTransaction);
        }

    }

    @Override
    public void onAccept(Transaction transaction) {
        transaction.transactionProcess = TransactionProcess.Completed;

        updateTransaction = transaction;
        Constant.network.updateTransactionRequest(transaction);
    }

    @Override
    public void onReject(Transaction transaction) {
        transaction.transactionProcess = TransactionProcess.Rejected;

        updateTransaction = transaction;
        Constant.network.updateTransactionRequest(transaction);
    }


}