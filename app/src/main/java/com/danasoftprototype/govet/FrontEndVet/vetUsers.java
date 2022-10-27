package com.danasoftprototype.govet.FrontEndVet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.FrontEndAdmin.adminSettings;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityGovethome3Binding;
import com.danasoftprototype.govet.databinding.ActivityVetUsersBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

public class vetUsers extends AppCompatActivity {

    ImageView drawerButton;
    DatabaseReference reference;

    private AppBarConfiguration mAppBarConfiguration;
    private com.danasoftprototype.govet.databinding.ActivityVetUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVetUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navView);
        DrawerLayout drawerLayout = (binding.drawerLayout);
        drawerButton = (binding.hamburger);

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setCheckedItem(R.id.nav_vetUsers);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplication(), govethome3.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_vetUsers:
                        item.setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_vetAppointment:
                        startActivity(new Intent(getApplication(), vetSchedule.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                    case R.id.nav_vetMonitoring:
                        startActivity(new Intent(getApplication(), vetMonitor.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                    case R.id.nav_vetSettings:
                        startActivity(new Intent(getApplication(), vetSettings.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                }

                return true;
            }
        });

    }
}