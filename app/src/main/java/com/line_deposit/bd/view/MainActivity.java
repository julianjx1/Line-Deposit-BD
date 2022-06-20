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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.line_deposit.bd.R;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.Authentication.LoginActivity;
import com.line_deposit.bd.view.fragment.admin.AdminHomeFragment;
import com.line_deposit.bd.view.fragment.user.HomeFragment;
import com.line_deposit.bd.view.fragment.user.ShowTransactionsFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;



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
        navUsername.setText(Constant.user.username);
        navBalance.setText(String.valueOf(Constant.user.balance));
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        switch (Constant.user.userType){
            case affiliate:
            case user:
                loadFragment(new HomeFragment());
                break;
            case admin:
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
}