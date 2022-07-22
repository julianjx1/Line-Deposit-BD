package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.line_deposit.bd.R;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.admin.affiliate.AffiliateFragment;
import com.line_deposit.bd.view.fragment.user.ShowTransactionsFragment;


public class AdminHomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_admin_home, container, false);
        MaterialButton withdrawRequestButton = view.findViewById(R.id.btn_withdraw_request);
        MaterialButton depositRequestButton = view.findViewById(R.id.btn_deposit_request);
        MaterialButton showTransactionButton = view.findViewById(R.id.btn_show_transactions);
        MaterialButton changeMobileButton = view.findViewById(R.id.btn_change_mobile);
        MaterialButton changeTransactionNumberButton = view.findViewById(R.id.btn_change_transaction_number);
        MaterialButton changeTransactionLimitButton = view.findViewById(R.id.btn_change_transaction_limit);
        MaterialButton addNewUserButton = view.findViewById(R.id.btn_add_new_userr);
        MaterialButton affiliaterButton = view.findViewById(R.id.btn_affiliate);
        MaterialButton advertiseButton = view.findViewById(R.id.btn_advertise);

        Constant.network.userList();
        Constant.network.getTransactionProcessList();
        if(requireActivity().getIntent().hasExtra("PaymentType")) {
            PaymentType paymentType =  PaymentType.valueOf(requireActivity().getIntent().getExtras().getString("PaymentType"));
            Bundle bundle = new Bundle();
            RequestTransactionFragment requestTransactionFragment = new RequestTransactionFragment();
            bundle.putString("request_type", paymentType.toString());
            requestTransactionFragment.setArguments(bundle);
            loadFragment(requestTransactionFragment);
        }
        withdrawRequestButton.setOnClickListener(v -> {
            RequestTransactionFragment requestTransactionFragment = new RequestTransactionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("request_type", PaymentType.Withdraw.toString());
            requestTransactionFragment.setArguments(bundle);
            loadFragment(requestTransactionFragment);});
        depositRequestButton.setOnClickListener(v -> {
            RequestTransactionFragment requestTransactionFragment = new RequestTransactionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("request_type", PaymentType.Deposit.toString());
            requestTransactionFragment.setArguments(bundle);
            loadFragment(requestTransactionFragment);});
        showTransactionButton.setOnClickListener(v -> loadFragment(new ShowTransactionsFragment()));
        changeMobileButton.setOnClickListener(v -> loadFragment(new ChangeMobileNumberFragment()));
        changeTransactionNumberButton.setOnClickListener(v -> loadFragment(new ChangeTransactionNumberFragment()));
        changeTransactionLimitButton.setOnClickListener(v -> loadFragment(new TransactionLimitFragment()));
        addNewUserButton.setOnClickListener(v -> loadFragment(new AddNewUserFragment()));
        affiliaterButton.setOnClickListener(v -> loadFragment(new AffiliateFragment()));
        advertiseButton.setOnClickListener(v -> loadFragment(new AdvertisementFragment()));
        return  view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("back");
        fragmentTransaction.commit();
    }
}