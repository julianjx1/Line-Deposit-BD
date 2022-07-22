package com.line_deposit.bd.view.fragment.user;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.line_deposit.bd.R;
import com.line_deposit.bd.model.Advertisement;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.admin.AdvertisementObserver;

import java.util.Objects;


public class HomeFragment extends Fragment implements AdvertisementObserver {

    TextView title, description;
    FloatingActionButton whatsapp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        MaterialButton withdrawButton = view.findViewById(R.id.btn_withdraw);
        MaterialButton depositButton = view.findViewById(R.id.btn_deposit);
        MaterialButton showTransactionButton = view.findViewById(R.id.btn_show_transactions);
        MaterialButton contactUsButton = view.findViewById(R.id.btn_contact_us);

        title= view.findViewById(R.id.advertisement_title);
        description  = view.findViewById(R.id.advertisement_description);

        whatsapp  = view.findViewById(R.id.whatsapp);

        title.setSelected(true);
        Constant.network.getTransactionProcessList();
        Constant.network.advertisementObserver = this;
        Constant.network.getAdvertisement();
        if(Constant.user.userType == UserType.affiliate)
            depositButton.setVisibility(View.GONE);
        if(requireActivity().getIntent().hasExtra("PaymentType")) {
            loadFragment(new ShowTransactionsFragment());
        }

        withdrawButton.setOnClickListener(v -> loadFragment(new WithdrawFragment()));
        depositButton.setOnClickListener(v -> loadFragment(new DepositFragment()));
        showTransactionButton.setOnClickListener(v -> loadFragment(new ShowTransactionsFragment()));
        contactUsButton.setOnClickListener(v -> loadFragment(new ContactUsFragment()));

        whatsapp.setOnClickListener(view1 -> {
            if (isWhatsappInstalled() && Constant.userMap.containsKey("admin")){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+88"+ Objects.requireNonNull(Constant.userMap.get("admin")).mobile));
                startActivity(intent);

            }else{
                Toast.makeText(requireContext(), "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }


    private  boolean isWhatsappInstalled(){
        PackageManager packageManager = requireContext().getPackageManager();
        boolean whatsAppInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsAppInstalled = true;
        }catch (PackageManager.NameNotFoundException e){
            whatsAppInstalled = false;
        }
        return  whatsAppInstalled;
    }

    @Override
    public void getAdvertisement(Advertisement advertisement) {
        if (advertisement != null){
            title.setText(advertisement.title);
            description.setText(advertisement.description);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("back");
        fragmentTransaction.commit();
    }
}