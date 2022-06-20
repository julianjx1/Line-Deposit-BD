package com.line_deposit.bd.view.fragment.user;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentContactUsBinding;
import com.line_deposit.bd.model.User;
import com.line_deposit.bd.utilites.Constant;


public class ContactUsFragment extends Fragment {
    FragmentContactUsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_contact_us, container, false);

        User user = Constant.userMap.get("admin");
        if(user != null && user.mobile != null)
            binding.mobile.setText(user.mobile);
        return binding.getRoot();
    }
}