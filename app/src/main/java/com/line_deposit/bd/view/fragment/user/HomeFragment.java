package com.line_deposit.bd.view.fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.line_deposit.bd.R;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.utilites.Constant;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        MaterialButton withdrawButton = view.findViewById(R.id.btn_withdraw);
        MaterialButton depositButton = view.findViewById(R.id.btn_deposit);
        MaterialButton showTransactionButton = view.findViewById(R.id.btn_show_transactions);
        MaterialButton contactUsButton = view.findViewById(R.id.btn_contact_us);

        Constant.network.getTransactionProcessList();

        if(Constant.user.userType == UserType.affiliate)
            depositButton.setVisibility(View.GONE);
        if(requireActivity().getIntent().hasExtra("PaymentType")) {
            loadFragment(new ShowTransactionsFragment());
        }

        withdrawButton.setOnClickListener(v -> loadFragment(new WithdrawFragment()));
        depositButton.setOnClickListener(v -> loadFragment(new DepositFragment()));
        showTransactionButton.setOnClickListener(v -> loadFragment(new ShowTransactionsFragment()));
        contactUsButton.setOnClickListener(v -> loadFragment(new ContactUsFragment()));


        return  view;
    }




    private void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("back");
        fragmentTransaction.commit();
    }
}