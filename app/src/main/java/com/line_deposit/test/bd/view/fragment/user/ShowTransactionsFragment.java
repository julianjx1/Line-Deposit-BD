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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowTransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowTransactionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowTransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowTransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowTransactionsFragment newInstance(String param1, String param2) {
        ShowTransactionsFragment fragment = new ShowTransactionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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