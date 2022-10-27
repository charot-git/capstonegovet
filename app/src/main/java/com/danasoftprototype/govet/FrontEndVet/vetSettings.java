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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.MainActivity;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.settings.aboutus;
import com.danasoftprototype.govet.settings.privacyterm;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class vetSettings extends AppCompatActivity {

    ImageView drawerButton;
    DatabaseReference reference;
    TextView display, privacyAndTerms, aboutUs, help, logout;

    private AppBarConfiguration mAppBarConfiguration;
    private com.danasoftprototype.govet.databinding.ActivityVetSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.danasoftprototype.govet.databinding.ActivityVetSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        logout = binding.logoutview;
        display = binding.displayview;
        privacyAndTerms = binding.privacyandtermsview;
        aboutUs = binding.aboutusview;
        help = binding.helpview;

        NavigationView navigationView = (binding.navView);
        DrawerLayout drawerLayout = (binding.drawerLayout);
        drawerButton = (binding.hamburger);

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setCheckedItem(R.id.nav_vetSettings);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplication(), govethome3.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_vetUsers:
                        startActivity(new Intent(getApplication(), vetUsers.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
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
                        item.setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), com.danasoftprototype.govet.settings.display.class);
                startActivity(intent);

            }
        });

        privacyAndTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), privacyterm.class);
                startActivity(intent);

            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), aboutus.class);
                startActivity(intent);

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), com.danasoftprototype.govet.settings.help.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod();
            }
        });
    }

    private void logoutMethod() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplication(), "Logout successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}