package com.line_deposit.bd.view.fragment.admin.affiliate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentAffiliateBinding;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.view.fragment.admin.AddNewUserFragment;

public class AffiliateFragment extends Fragment {

   FragmentAffiliateBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_affiliate, container, false);
        binding.btnAddNewAffiliate.setOnClickListener(v -> {
            AddNewUserFragment userFragment = new AddNewUserFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userType", UserType.affiliate.toString());
            userFragment.setArguments(bundle);
            loadFragment(userFragment);
        });
        binding.btnAddAffiliateMember.setOnClickListener(v -> {
            AddAffiliateMemberFragment addAffiliateMemberFragment = new AddAffiliateMemberFragment();
            loadFragment(addAffiliateMemberFragment);
        });

        return binding.getRoot();
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}