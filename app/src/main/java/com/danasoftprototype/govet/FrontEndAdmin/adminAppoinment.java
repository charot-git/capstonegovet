package com.danasoftprototype.govet.FrontEndAdmin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.danasoftprototype.govet.AdminAdapter;
import com.danasoftprototype.govet.AppointmentAdapter;
import com.danasoftprototype.govet.FrontEnd.Bookings;
import com.danasoftprototype.govet.ModelUser;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.User;
import com.danasoftprototype.govet.databinding.ActivityAdminAppoinmentBinding;
import com.danasoftprototype.govet.databinding.ActivityAdminUsersBinding;
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

public class adminAppoinment extends AppCompatActivity {

    ImageView drawerButton;
    RecyclerView recyclerView;
    List<Bookings>bookingsList;
    List<ModelUser>modelUsers;
    AppointmentAdapter appointmentAdapter;
    Spinner months;
    String month;

    private ActivityAdminAppoinmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAppoinmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navView);
        navigationView.setCheckedItem(R.id.nav_adminAppointment);

        DrawerLayout drawerLayout = (binding.drawerLayout);
        drawerButton = (binding.hamburger);

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        bookingsList = new ArrayList<>();
        modelUsers = new ArrayList<>();

        months = binding.spinner3;

        ArrayAdapter<CharSequence>adapterMonth = ArrayAdapter.createFromResource(this,R.array.months, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterMonth.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        months.setAdapter(adapterMonth);
        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = parent.getItemAtPosition(position).toString();


                //if january then 1, then filter by month 1 from database
                if (month.equals("January")){

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getAllBooks();

            }
        });


        getAllBooks();

        navigationView.setCheckedItem(R.id.nav_adminAppointment);
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
                        startActivity(new Intent(getApplication(),adminUsers.class));
                        finishAfterTransition();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        item.setChecked(true);
                        break;
                    case R.id.nav_adminAppointment:
                        item.setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
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


    }

    private void getAllBooks() {
        DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference("Bookings");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bookings").child("bookingDetails");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingsList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Bookings bookings = ds.getValue(Bookings.class);
                    if(!bookings.getName().equals(firebaseUser.getDisplayName())){
                        bookingsList.add(bookings);
                    }

                    appointmentAdapter = new AppointmentAdapter(getApplication(), bookingsList);
                    recyclerView.setAdapter(appointmentAdapter);
                    recyclerView.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}