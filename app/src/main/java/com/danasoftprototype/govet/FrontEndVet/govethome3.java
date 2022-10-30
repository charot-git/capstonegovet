package com.danasoftprototype.govet.FrontEndVet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.FrontEndAdmin.adminAppoinment;
import com.danasoftprototype.govet.FrontEndAdmin.adminOnlinePayments;
import com.danasoftprototype.govet.FrontEndAdmin.adminSettings;
import com.danasoftprototype.govet.FrontEndAdmin.adminUsers;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityGovethome3Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class govethome3 extends AppCompatActivity {
    ImageView drawerButton;
    TextView postAdmin;
    Button postButton;
    DatabaseReference reference;

    private AppBarConfiguration mAppBarConfiguration;
    private com.danasoftprototype.govet.databinding.ActivityGovethome3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGovethome3Binding.inflate(getLayoutInflater());
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

        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        item.setChecked(true);
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
                        startActivity(new Intent(getApplication(), vetSettings.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                }

                return true;
            }
        });

        ifAdminHasPost();

    }

    private void ifAdminHasPost() {

        postAdmin = binding.annHome;
        reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.child("adminPosts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        DataSnapshot dataSnapshot = task.getResult();

                        String postFromDatabase = String.valueOf(dataSnapshot.child("post").getValue());
                        postAdmin.setText(postFromDatabase);
                    }

                }

            }
        });
    }

}