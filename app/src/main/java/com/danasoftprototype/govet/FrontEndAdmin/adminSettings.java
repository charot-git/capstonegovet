package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.MainActivity;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityAdminOnlinePaymentsBinding;
import com.danasoftprototype.govet.databinding.ActivityAdminSettingsBinding;
import com.danasoftprototype.govet.settings.aboutus;
import com.danasoftprototype.govet.settings.display;
import com.danasoftprototype.govet.settings.help;
import com.danasoftprototype.govet.settings.privacyterm;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class adminSettings extends AppCompatActivity {

    ImageView drawerButton;
    TextView display, privacyAndTerms, aboutUs, help, logout;

    private ActivityAdminSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navView);
        navigationView.setCheckedItem(R.id.nav_adminSettings);


        DrawerLayout drawerLayout = (binding.drawerLayout);
        drawerButton = (binding.hamburger);

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


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

        display = binding.displayview;
        privacyAndTerms = binding.privacyandtermsview;
        aboutUs = binding.aboutusview;
        help = binding.helpview;
        logout = binding.logoutview;



        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMethod();
            }
        });

        privacyAndTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyAndTermsMethod();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutUsMethod();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpMethod();
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

    private void helpMethod() {
        Intent intent = new Intent(getApplication(), com.danasoftprototype.govet.settings.help.class);
        startActivity(intent);
    }

    private void aboutUsMethod() {
        Intent intent = new Intent(getApplication(), aboutus.class);
        startActivity(intent);
    }

    private void privacyAndTermsMethod() {
        Intent intent = new Intent(getApplication(), privacyterm.class);
        startActivity(intent);
    }

    private void displayMethod() {
        Intent intent = new Intent(getApplication(), com.danasoftprototype.govet.settings.display.class);
        startActivity(intent);
    }
}