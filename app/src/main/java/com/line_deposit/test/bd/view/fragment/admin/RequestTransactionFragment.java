package com.line_deposit.test.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.FragmentRequestTransactionBinding;
import com.line_deposit.test.bd.model.PaymentType;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.TransactionProcess;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.model.UserType;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.adapter.RequestTransactionObserver;
import com.line_deposit.test.bd.view.adapter.RequestTransactionsAdapter;
import com.line_deposit.test.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RequestTransactionFragment extends Fragment implements TransactionObserver, TransactionRequestObserver, RequestTransactionObserver {

    FragmentRequestTransactionBinding binding;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private RequestTransactionsAdapter adapter;
    private Transaction updateTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(
                 inflater, R.layout.fragment_request_transaction, container, false);
        View view = binding.getRoot();
        RecyclerView recyclerView = binding.requestTransactionList;
        if (getArguments().containsKey("request_type"))
        Constant.network.userTransactionRequest(PaymentType.valueOf(getArguments().getString("request_type")));
        Constant.network.transactionRequestObserver = this;
        Constant.network.transactionObserver = this;
        return view;
    }

    @Override
    public void transactionRequstions(ArrayList<Transaction> transactions) {
        Collections.reverse(transactions);
        adapter = new RequestTransactionsAdapter(transactions, Constant.userMap, requireContext(), this);
        binding.requestTransactionList.setHasFixedSize(true);
        binding.requestTransactionList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.requestTransactionList.setAdapter(adapter);
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
        Constant.network.updateTransactionRequest(transaction);
        updateTransaction = transaction;
    }

    @Override
    public void onReject(Transaction transaction) {
        transaction.transactionProcess = TransactionProcess.Rejected;
        Constant.network.updateTransactionRequest(transaction);
        updateTransaction = transaction;
    }


}