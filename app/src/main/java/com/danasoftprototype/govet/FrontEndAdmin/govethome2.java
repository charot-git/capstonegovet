package com.danasoftprototype.govet.FrontEndAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;

import com.danasoftprototype.govet.databinding.ActivityGovethome2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class govethome2 extends AppCompatActivity {

    private ActivityGovethome2Binding binding;
    ImageView drawerButton;
    TextView postAdmin;
    Button postButton;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGovethome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = (binding.navView);
        DrawerLayout drawerLayout = (binding.drawerLayout);
        drawerButton = (binding.hamburger);
        postAdmin = binding.adminPost;
        postButton = binding.button3;

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), announce.class));
            }
        });

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
                        item.setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_adminUsers:
                        startActivity(new Intent(getApplication(),adminUsers.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAfterTransition();
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

        reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.child("adminPosts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();

                        String postFromDatabase = String.valueOf(dataSnapshot.child("post").getValue());
                        postAdmin.setText(postFromDatabase);
                        postAdmin.setVisibility(View.VISIBLE);
                    }

                }

            }
        });

    }
}