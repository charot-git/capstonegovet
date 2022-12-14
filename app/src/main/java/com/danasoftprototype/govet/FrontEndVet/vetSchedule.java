package com.danasoftprototype.govet.FrontEndVet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
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

import com.danasoftprototype.govet.AppointmentAdapter;
import com.danasoftprototype.govet.FrontEnd.Bookings;
import com.danasoftprototype.govet.FrontEndAdmin.adminSettings;
import com.danasoftprototype.govet.ModelUser;
import com.danasoftprototype.govet.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class vetSchedule extends AppCompatActivity {

    ImageView drawerButton;
    DatabaseReference reference;
    RecyclerView recyclerView;
    List<Bookings> bookingsList;
    List<ModelUser> modelUsers;
    AppointmentAdapter appointmentAdapter;
    Spinner months;
    String month;

    private AppBarConfiguration mAppBarConfiguration;
    private com.danasoftprototype.govet.databinding.ActivityVetScheduleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.danasoftprototype.govet.databinding.ActivityVetScheduleBinding.inflate(getLayoutInflater());
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

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        bookingsList = new ArrayList<>();
        modelUsers = new ArrayList<>();
        months = binding.spinner3;

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this,R.array.months, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterMonth.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        months.setAdapter(adapterMonth);
        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view,int position, long id){
                month = parent.getItemAtPosition(position).toString();


                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Bookings").child("bookingDetails");
                Query queryByMonth;
                //if january then 1, then filter by month 1 from database
                if (month.equals("All")) {
                    getAllBooks();
                }else if (month.equals("January")) {
                    queryByMonth = db.orderByChild("month").equalTo("1");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("February")) {
                    queryByMonth = db.orderByChild("month").equalTo("2");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("March")) {
                    queryByMonth = db.orderByChild("month").equalTo("3");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("April")) {
                    queryByMonth = db.orderByChild("month").equalTo("4");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("May")) {
                    queryByMonth = db.orderByChild("month").equalTo("5");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("June")) {
                    queryByMonth = db.orderByChild("month").equalTo("6");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("July")) {
                    queryByMonth = db.orderByChild("month").equalTo("7");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("August")) {
                    queryByMonth = db.orderByChild("month").equalTo("8");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("September")) {
                    queryByMonth = db.orderByChild("month").equalTo("9");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("October")) {
                    queryByMonth = db.orderByChild("month").equalTo("10");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("November")) {
                    queryByMonth = db.orderByChild("month").equalTo("11");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                } else if (month.equals("December")) {
                    queryByMonth = db.orderByChild("month").equalTo("12");
                    queryByMonth.addListenerForSingleValueEvent(valueEventListener);
                }

            }


            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dSnapshot) {
                    bookingsList.clear();
                    if (dSnapshot.exists()) ;
                    for (DataSnapshot dataSnapshot : dSnapshot.getChildren()) {
                        Bookings bookings = dataSnapshot.getValue(Bookings.class);
                        bookingsList.add(bookings);
                    }
                    appointmentAdapter = new AppointmentAdapter(getApplication(), bookingsList);
                    recyclerView.setAdapter(appointmentAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getAllBooks();

            }
        });

        navigationView.setCheckedItem(R.id.nav_vetAppointment);
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
                        item.setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
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