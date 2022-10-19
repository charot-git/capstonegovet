package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityAdminOnlinePaymentsBinding;
import com.danasoftprototype.govet.databinding.ActivityAdminSettingsBinding;
import com.google.android.material.navigation.NavigationView;

public class adminSettings extends AppCompatActivity {

    private ActivityAdminSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navView);
        navigationView.setCheckedItem(R.id.nav_adminSettings);

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
                        startActivity(new Intent(getApplication(),adminAppoinment.class));
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminOnlinePayment:
                        startActivity(new Intent(getApplication(),adminOnlinePayments.class));
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminSettings:
                        item.setChecked(true);
                        break;
                }

                return true;
            }
        });
    }
}