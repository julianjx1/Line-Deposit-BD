package com.line_deposit.test.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.FragmentChangeTransactionNumberBinding;


public class ChangeTransactionNumberFragment extends Fragment {

    FragmentChangeTransactionNumberBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_transaction_number,container,false);

        String[] type = new String[] {"BKash", "Nagad", "UPay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,type);
        binding.accountType.setAdapter(adapter);

        return binding.getRoot();
    }
}