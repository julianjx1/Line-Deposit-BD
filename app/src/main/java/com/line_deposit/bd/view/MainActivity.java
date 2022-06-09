package com.line_deposit.bd.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.line_deposit.bd.R;
import com.line_deposit.bd.view.fragment.CreateAccountFragment;
import com.line_deposit.bd.view.fragment.HomeFragment;
import com.line_deposit.bd.view.fragment.IncomeFragment;
import com.line_deposit.bd.view.fragment.ReferalLinkFragment;
import com.line_deposit.bd.view.fragment.SubmitYoutubeChannelFragment;

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

        setSupportActionBar(toolbar);

      //  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

      //  drawerLayout.addDrawerListener(toggle);

   //     toggle.syncState();
        loadFragment(new HomeFragment());
        setTitle(R.string.home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                setTitle(item.getTitle());
                switch (id){

                    case R.id.home:
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.create_account:
                        loadFragment(new CreateAccountFragment());

                        break;
                    case R.id.submit_youtube_video:
                        loadFragment(new SubmitYoutubeChannelFragment());
                        break;
                    case R.id.income:
                        loadFragment(new IncomeFragment());
                        break;
                    case R.id.referal_link:
                        loadFragment(new ReferalLinkFragment());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + id);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
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
}