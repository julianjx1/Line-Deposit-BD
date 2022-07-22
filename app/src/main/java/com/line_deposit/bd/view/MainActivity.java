package com.line_deposit.bd.view;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.line_deposit.bd.R;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.Authentication.LoginActivity;
import com.line_deposit.bd.view.fragment.admin.AdminHomeFragment;
import com.line_deposit.bd.view.fragment.user.HomeFragment;
import com.line_deposit.bd.view.fragment.user.ShowTransactionsFragment;

public class MainActivity extends AppCompatActivity implements AdminStatusObserver{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView status;
    Switch statusSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        TextView navBalance = (TextView) headerView.findViewById(R.id.balance);
        LinearLayout adminStatusLayout = findViewById(R.id.admin_status_layout);
        status = findViewById(R.id.admin_status);
        statusSwitch = findViewById(R.id.status_setup);
        navUsername.setText(Constant.user.username);
        navBalance.setText(String.valueOf(Constant.user.balance));
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        Constant.network.adminStatusObserver = this;
        Constant.network.isAdminActive();

        switch (Constant.user.userType){
            case affiliate:
            case user:
                statusSwitch.setVisibility(View.GONE);
                status.setVisibility(View.VISIBLE);
                loadFragment(new HomeFragment());
                break;
            case admin:
                statusSwitch.setVisibility(View.VISIBLE);
                status.setVisibility(View.GONE);
                loadFragment(new AdminHomeFragment());
                break;
        }
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.containsKey("PaymentType"))
            {
//                setContentView(R.layout.viewmain);
//                // extract the extra-data in the Notification
//                String msg = extras.getString("NotificationMessage");
//                txtView = (TextView) findViewById(R.id.txtMessage);
//                txtView.setText(msg);
            }
        }
        if(getIntent().hasExtra("PaymentType")) {
            loadFragment(new ShowTransactionsFragment());
        }

        setTitle(R.string.home);
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();
            setTitle(item.getTitle());
            switch (id){

                case R.id.logout:
                    Constant.removeUserInformation();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + id);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        statusSwitch.setOnClickListener(view -> {
            Constant.network.updateUserStatus(statusSwitch.isChecked());
            if (statusSwitch.isChecked()){
                Toast.makeText(MainActivity.this, "Your are now online", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Your are now offline", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {

        Log.d("test", "onBackPressed: "+getFragmentManager().getBackStackEntryCount());
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public void isAdminActive(Boolean status) {
        statusSwitch.setChecked(status);
        if (status)
        {
         this.status.setText("Online");
         this.status.setBackground(getDrawable(R.drawable.green_round_background));
        }
        else{
            this.status.setText("Offline");
            this.status.setBackground(getDrawable(R.drawable.red_round_background));
        }

    }
}