package com.line_deposit.test.bd.view.fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.model.PaymentType;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.TransactionProcess;
import com.line_deposit.test.bd.view.adapter.TransctionsAdapter;

import java.util.ArrayList;

public class ShowTransactionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_transactions, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.transaction_list);

        ArrayList<Transaction> transactions = new ArrayList<>();
//        transactions.add(new Transaction("Xyz","+123", "123sdfsd", "Bkash", TransactionProcess.Completed, PaymentType.Deposit, 500, "2022/12/06" ));
//        transactions.add(new Transaction("xyz","+345", "345sdfsd", "Nagad", TransactionProcess.Processing, PaymentType.Withdraw, 1500 ,"2022/12/06"));
//        transactions.add(new Transaction("Xyz","+3465", "675sdfsd", "Upay", TransactionProcess.Rejected, PaymentType.Deposit, 2500 ,"2022/12/06"));

        TransctionsAdapter adapter = new TransctionsAdapter(transactions, requireContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return  view;
    }
}