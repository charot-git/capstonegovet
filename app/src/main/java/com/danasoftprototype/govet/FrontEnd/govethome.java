package com.danasoftprototype.govet.FrontEnd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.danasoftprototype.govet.databinding.ActivityGovethomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class govethome extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityGovethomeBinding binding;
    FirebaseUser mAuth;
    StorageReference storageReference;
    DatabaseReference reference;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String email1 = null;
        String name1 = null;
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth != null) {
            for (UserInfo profile : mAuth.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();

                email1 = email;
                name1 = name;


            }
        }

        storageReference = FirebaseStorage.getInstance().getReference();

        binding = ActivityGovethomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        floatingActionButton = findViewById(R.id.fab);

        getBookingData();

        setSupportActionBar(binding.appBarGovethome.toolbar);
        binding.appBarGovethome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(govethome.this, booking.class));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_users,
                R.id.nav_sched,
                R.id.nav_shop,
                R.id.nav_monitor,
                R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_govethome);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationView mnavigationView = (NavigationView) findViewById(R.id.nav_view);
        View headearView = mnavigationView.getHeaderView(0);

        TextView userEmail = headearView.findViewById(R.id.useremail);
        TextView userName = headearView.findViewById(R.id.username);
        ImageView imageView = headearView.findViewById(R.id.imageView);


        StorageReference profileRef = storageReference.child("images/" + mAuth.getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(250,250).centerCrop().into(imageView);
            }
        });
        userEmail.setText(email1);
        userName.setText(name1);

    }

    private void getBookingData() {
        String booking = "Bookings";
        reference =FirebaseDatabase.getInstance().getReference("/Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(booking).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        floatingActionButton.setVisibility(View.INVISIBLE);
                    }
                    else {
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_govethome);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}