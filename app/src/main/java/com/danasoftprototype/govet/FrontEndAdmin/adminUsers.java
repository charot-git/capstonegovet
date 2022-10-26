package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

import com.danasoftprototype.govet.AdapterUsers;
import com.danasoftprototype.govet.AdminAdapter;
import com.danasoftprototype.govet.ModelUser;
import com.danasoftprototype.govet.R;
import com.google.android.material.navigation.NavigationView;
import com.danasoftprototype.govet.databinding.ActivityAdminUsersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminUsers extends AppCompatActivity {

    ImageView drawerButton;
    RecyclerView recyclerView;
    List<ModelUser> userList;
    AdminAdapter adapterUsers;
    SearchView searchView;


    private ActivityAdminUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navViewUsers);
        DrawerLayout drawerLayout = (binding.drawerUsers);
        drawerButton = (binding.hamburger);

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.setVisibility(View.VISIBLE);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setCheckedItem(R.id.nav_adminUsers);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplication(), govethome2.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminUsers:
                        item.setChecked(true);
                        break;
                    case R.id.nav_adminAppointment:
                        startActivity(new Intent(getApplication(),adminAppoinment.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminOnlinePayment:
                        startActivity(new Intent(getApplication(),adminOnlinePayments.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                    case R.id.nav_adminSettings:
                        startActivity(new Intent(getApplication(),adminSettings.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
                        break;
                }

                return true;
            }
        });

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        userList = new ArrayList<>();

        getAllUsers();

    }

    private void getAllUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    ModelUser modelUser = ds.getValue(ModelUser.class);
                    if(!modelUser.getUid().equals(firebaseUser.getUid())){
                        userList.add(modelUser);
                    }

                    adapterUsers = new AdminAdapter(getApplication(), userList);
                    recyclerView.setAdapter(adapterUsers);
                    recyclerView.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}