package com.line_deposit.test.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.FragmentRequestTransactionBinding;
import com.line_deposit.test.bd.model.PaymentType;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.TransactionProcess;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.model.UserType;
import com.line_deposit.test.bd.view.adapter.RequestTransactionsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RequestTransactionFragment extends Fragment {

    FragmentRequestTransactionBinding binding;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(
                 inflater, R.layout.fragment_request_transaction, container, false);
        View view = binding.getRoot();
        RecyclerView recyclerView = binding.requestTransactionList;

        ArrayList<Transaction> transactions = new ArrayList<>();
//        transactions.add(new Transaction("Xyz","+123", "123sdfsd", "Bkash", TransactionProcess.Completed, PaymentType.Deposit, 500, "2022/12/06" ));
//        transactions.add(new Transaction("xyz","+345", "345sdfsd", "Nagad", TransactionProcess.Processing, PaymentType.Withdraw, 1500 ,"2022/12/06"));
//        transactions.add(new Transaction("Xyz","+3465", "675sdfsd", "Upay", TransactionProcess.Rejected, PaymentType.Deposit, 2500 ,"2022/12/06"));


        Map<String, User> userMap = new HashMap<>();
        userMap.put("Xyz", new User("Xyz", "sdf15616", UserType.user));
        RequestTransactionsAdapter adapter = new RequestTransactionsAdapter(transactions, userMap, requireContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}