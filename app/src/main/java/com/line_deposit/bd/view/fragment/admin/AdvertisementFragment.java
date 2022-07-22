package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentAdvertisementBinding;
import com.line_deposit.bd.databinding.FragmentAffiliateBinding;
import com.line_deposit.bd.model.Advertisement;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;


public class AdvertisementFragment extends Fragment implements AdvertisementObserver, TransactionObserver {

   FragmentAdvertisementBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_advertisement, container, false);
        binding.setAdvertisement(new Advertisement());
        Constant.network.transactionObserver = this;
        Constant.network.advertisementObserver = this;
        Constant.network.getAdvertisement();
        binding.btnSave.setOnClickListener(view -> {
            Constant.network.setAdvertisement(binding.getAdvertisement());
        });

        return binding.getRoot();
    }

    @Override
    public void getAdvertisement(Advertisement advertisement) {
        binding.setAdvertisement(advertisement);
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}