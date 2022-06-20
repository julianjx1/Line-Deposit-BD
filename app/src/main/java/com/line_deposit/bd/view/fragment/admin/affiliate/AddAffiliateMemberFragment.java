package com.line_deposit.bd.view.fragment.admin.affiliate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentAddAffiliateMemberBinding;
import com.line_deposit.bd.model.User;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;

import java.util.Map;
import java.util.Objects;


public class AddAffiliateMemberFragment extends Fragment implements TransactionObserver, MembersObserver {

    FragmentAddAffiliateMemberBinding binding;
    private boolean showToast = true;
    private User affiliateUser, user;
    private boolean adduser = false;
    private boolean removeuser = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_affiliate_member, container, false);
       Constant.network.transactionObserver = this;
       Constant.network.membersObserver = this;
        binding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                User user = Constant.userMap.get(Objects.requireNonNull(binding.etUsername.getText()).toString());
                if(user != null && user.affiliateUser != null){
                    binding.etAffiliateUsername.setText(user.affiliateUser);
                }
                else{
                    binding.etAffiliateUsername.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnSave.setOnClickListener(v -> {
            this.showToast = true;
           String affiliateUserName = Objects.requireNonNull(binding.etAffiliateUsername.getText()).toString();
           String userName = Objects.requireNonNull(binding.etUsername.getText()).toString();
            if (userName.isEmpty()){
                Toast.makeText(requireContext(), "Username must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            if (affiliateUserName.isEmpty()){
                Toast.makeText(requireContext(), "Affiliate username must be given", Toast.LENGTH_SHORT).show();
                return;
            }
             affiliateUser = Constant.userMap.get(affiliateUserName);
             user = Constant.userMap.get(userName);
            if (user == null){
                Toast.makeText(requireContext(), "Invalid user.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (user.userType != UserType.user){
                Toast.makeText(requireContext(), "Username is not a normal user.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (affiliateUser == null){
                Toast.makeText(requireContext(), "Invalid affiliate user.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (affiliateUser.userType != UserType.affiliate){
                Toast.makeText(requireContext(), "AffiliateUser is not an affiliate user.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(user.affiliateUser  == null || user.affiliateUser.isEmpty()) {
                adduser = true;
                Constant.network.affiliateMembersList(affiliateUser, "add");
            }else if(user.affiliateUser.compareToIgnoreCase(affiliateUser.username) == 0){
                Toast.makeText(requireContext(), "This user has already affiliated under this affiliated user", Toast.LENGTH_SHORT).show();
            }
            else {
                removeuser = true;
                Constant.network.affiliateMembersList(Objects.requireNonNull(Constant.userMap.get(user.affiliateUser)), "remove");
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        if (showToast) {
            showToast = false;
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            binding.etUsername.setText("");
            binding.etAffiliateUsername.setText("");
        }
    }

    @Override
    public void membersList(Map<String, String> users, String purpose) {
        if(purpose.compareToIgnoreCase("remove") == 0 && removeuser){
            removeuser = false;
            for(String user: users.keySet()){
                if(Objects.requireNonNull(users.get(user)).compareToIgnoreCase(this.user.username) == 0){
                    Constant.network.removeAffiliateMember(this.user.affiliateUser, user);
                    adduser = true;
                    Constant.network.affiliateMembersList(affiliateUser, "add");
                    break;
                }
            }
        }
        else {
            if(adduser) {
                adduser = false;
                boolean notUsed = true;
                for (String user : users.keySet()) {
                    if (Objects.requireNonNull(users.get(user)).compareToIgnoreCase(this.user.username) == 0) {
                        Toast.makeText(requireContext(), "This user has already affiliated under this affiliated user", Toast.LENGTH_SHORT).show();
                        notUsed = false;
                        break;
                    }
                }
                if (notUsed) {
                    Constant.network.addAffiliateMember(user, affiliateUser);
                    this.user.affiliateUser = affiliateUser.username;
                    Constant.network.addUser(this.user);
                }
            }
        }



    }
}