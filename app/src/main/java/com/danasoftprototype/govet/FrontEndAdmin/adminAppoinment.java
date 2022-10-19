package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityAdminAppoinmentBinding;
import com.danasoftprototype.govet.databinding.ActivityAdminUsersBinding;
import com.google.android.material.navigation.NavigationView;

public class adminAppoinment extends AppCompatActivity {

    private ActivityAdminAppoinmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAppoinmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navView);

        navigationView.setCheckedItem(R.id.nav_adminAppointment);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplication(), govethome2.class));
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminUsers:
                        startActivity(new Intent(getApplication(),adminUsers.class));
                        finishAfterTransition();
                        item.setChecked(true);
                        break;
                    case R.id.nav_adminAppointment:
                        item.setChecked(true);
                        break;
                    case R.id.nav_adminOnlinePayment:
                        startActivity(new Intent(getApplication(),adminOnlinePayments.class));
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminSettings:
                        startActivity(new Intent(getApplication(),adminSettings.class));
                        finishAfterTransition();
                        break;
                }

                return true;
            }
        });

    }
}