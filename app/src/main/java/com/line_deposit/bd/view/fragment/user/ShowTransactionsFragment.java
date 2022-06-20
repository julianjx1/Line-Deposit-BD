package com.line_deposit.bd.view.fragment.user;

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
import com.line_deposit.bd.databinding.FragmentShowTransactionsBinding;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.model.TransactionProcess;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.adapter.RequestTransactionObserver;
import com.line_deposit.bd.view.adapter.RequestTransactionsAdapter;
import com.line_deposit.bd.view.adapter.TransctionsAdapter;
import com.line_deposit.bd.view.fragment.admin.TransactionRequestObserver;

import java.util.ArrayList;
import java.util.Collections;

public class ShowTransactionsFragment extends Fragment implements TransactionObserver,TransactionRequestObserver, RequestTransactionObserver {

    FragmentShowTransactionsBinding binding;
    private Transaction updateTransaction;
    private TransctionsAdapter adapter;
    private RequestTransactionsAdapter requestTransactionsAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_show_transactions, container, false);
        View view = binding.getRoot();
        Constant.network.transactionObserver = this;
        Constant.network.transactionRequestObserver = this;
        binding.transactionList.setHasFixedSize(true);
        binding.transactionList.setLayoutManager(new LinearLayoutManager(requireContext()));
        switch (Constant.user.userType){
            case admin:
                requestTransactionsAdapter = new RequestTransactionsAdapter(new ArrayList<>(),  Constant.userMap,requireContext(), this, true);
                binding.transactionList.setAdapter(requestTransactionsAdapter);
                Constant.network.showAdminTransaction();
                break;
            case affiliate:
                requestTransactionsAdapter = new RequestTransactionsAdapter(new ArrayList<>(),  Constant.userMap,requireContext(), this, true);
                binding.transactionList.setAdapter(requestTransactionsAdapter);
                Constant.network.showAffiliateTransaction();
                break;
            case user:
                adapter = new TransctionsAdapter(new ArrayList<>(),  requireContext());
                binding.transactionList.setAdapter(adapter);
                Constant.network.userTransactionRequest(PaymentType.Withdraw, Constant.user.username);
                Constant.network.userTransactionRequest(PaymentType.Deposit, Constant.user.username);
                Constant.network.showUserTransaction();
                break;
        }

        return view;
    }

    @Override
    public void transactionRequests(ArrayList<Transaction> transactions) {
        Collections.reverse(transactions);
        if(adapter != null)
        adapter.updateList(transactions);
        else
            requestTransactionsAdapter.updateList(transactions);
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

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        if (success){
            requestTransactionsAdapter.changedInformation(updateTransaction);
        }
    }
}