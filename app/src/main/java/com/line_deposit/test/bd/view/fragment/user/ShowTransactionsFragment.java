package com.line_deposit.test.bd.view.fragment.user;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.FragmentShowTransactionsBinding;
import com.line_deposit.test.bd.model.PaymentType;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.TransactionProcess;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.adapter.RequestTransactionsAdapter;
import com.line_deposit.test.bd.view.adapter.TransctionsAdapter;
import com.line_deposit.test.bd.view.fragment.admin.TransactionRequestObserver;

import java.util.ArrayList;
import java.util.Collections;

public class ShowTransactionsFragment extends Fragment implements TransactionRequestObserver {

    FragmentShowTransactionsBinding binding;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private TransctionsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_show_transactions, container, false);
        View view = binding.getRoot();
        Constant.network.transactionRequestObserver = this;
        switch (Constant.user.userType){
            case admin:
                Constant.network.showAdminTransaction();
                break;
            case user:
                Constant.network.userTransactionRequest(PaymentType.Withdraw, Constant.user.username);
                Constant.network.userTransactionRequest(PaymentType.Deposit, Constant.user.username);
                Constant.network.showUserTransaction();
                break;
        }
        adapter = new TransctionsAdapter(transactions,  requireContext());
        binding.transactionList.setHasFixedSize(true);
        binding.transactionList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.transactionList.setAdapter(adapter);
        return view;
    }

    @Override
    public void transactionRequstions(ArrayList<Transaction> transactions) {
        Collections.reverse(transactions);
        adapter.updateList(transactions);
    }
}