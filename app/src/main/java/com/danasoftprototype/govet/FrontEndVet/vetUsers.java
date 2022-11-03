package com.danasoftprototype.govet.FrontEndVet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.AdapterUsers;
import com.danasoftprototype.govet.FrontEndAdmin.adminSettings;
import com.danasoftprototype.govet.ModelUser;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityGovethome3Binding;
import com.danasoftprototype.govet.databinding.ActivityVetUsersBinding;
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

public class vetUsers extends AppCompatActivity {

    ImageView drawerButton;
    DatabaseReference reference;
    RecyclerView recyclerView;
    List<ModelUser> userList;
    TextView test;
    AdapterUsers adapterUsers;
    SearchView searchView;

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
                switch (item.getItemId()) {
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


        recyclerView = binding.userRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        searchView = binding.searchBar;
        userList = new ArrayList<>();
        getAllUsers();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<ModelUser> filteredList = new ArrayList<>();
        for (ModelUser item : userList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterUsers.filteredList(filteredList);
    }

    private void getAllUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelUser modelUser = ds.getValue(ModelUser.class);
                    if (!modelUser.getUid().equals(firebaseUser.getUid())) {
                        userList.add(modelUser);
                    }

                    adapterUsers = new AdapterUsers(getApplication(), userList);
                    recyclerView.setAdapter(adapterUsers);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}