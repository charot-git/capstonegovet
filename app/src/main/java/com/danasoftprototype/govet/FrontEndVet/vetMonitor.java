package com.danasoftprototype.govet.FrontEndVet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class vetMonitor extends AppCompatActivity {

    ImageView drawerButton;
    RecyclerView recyclerView;
    List<MonitoringUsers> monitoringUsersList;
    MonitoringAdapterVet monitoringAdapterVet;
    TextView clients;

    public static String keyValue;

    private com.danasoftprototype.govet.databinding.ActivityVetMonitorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.danasoftprototype.govet.databinding.ActivityVetMonitorBinding.inflate(getLayoutInflater());
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


        navigationView.setCheckedItem(R.id.nav_vetMonitoring);
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
                        item.setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
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

        clients = binding.monitorClients;
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        monitoringUsersList = new ArrayList<>();

        getAllMonitoringUsers();
    }

    private void getAllMonitoringUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = root.child("Monitoring");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monitoringUsersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){


                    MonitoringUsers monitoringUsers = ds.getValue(MonitoringUsers.class);
                    keyValue = ds.getRef().getKey().toString();
                    monitoringUsers.setUid(keyValue);

                    monitoringUsersList.add(monitoringUsers);



                    monitoringAdapterVet = new MonitoringAdapterVet(getApplication(), monitoringUsersList);
                    recyclerView.setAdapter(monitoringAdapterVet);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}