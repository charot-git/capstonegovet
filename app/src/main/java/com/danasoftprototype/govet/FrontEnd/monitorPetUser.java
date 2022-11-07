package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.UserLogs;
import com.danasoftprototype.govet.chatActivity;
import com.danasoftprototype.govet.databinding.ActivityMonitorPetUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class monitorPetUser extends AppCompatActivity {

    ImageView dp,back;
    TextView name, breed, status, date, time;
    Button contact, history;

    String userName, userUsername, userImage, userEmail, imageOfCurrentUser;

    ActivityMonitorPetUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonitorPetUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String petPic = getIntent().getStringExtra("dp");
        String petName = getIntent().getStringExtra("name");
        String petBreed = getIntent().getStringExtra("breed");
        String petStatus = getIntent().getStringExtra("status");
        String petDate = getIntent().getStringExtra("date");
        String petTime = getIntent().getStringExtra("time");

        dp = binding.profilepic2;
        name = binding.monitorname;
        breed = binding.monitorbreed;
        status = binding.petStatus;
        date = binding.date;
        time = binding.time;
        back = binding.back;
        contact = binding.button2;
        history = binding.historyMonitorbut;

        Picasso.get().load(petPic).placeholder(R.drawable.logogv).into(dp);

        name.setText(petName);
        breed.setText(petBreed);
        status.setText("Status : " +petStatus);
        date.setText("Date : " + petDate);
        time.setText("Time : " + petTime);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child("6UAPpqZQCPUSlxaLYcWNOBq4pnm1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot = task.getResult();

                        userName = String.valueOf(dataSnapshot.child("name").getValue());
                        userUsername = String.valueOf(dataSnapshot.child("username").getValue());
                        userImage = String.valueOf(dataSnapshot.child("image").getValue());
                        userEmail = String.valueOf(dataSnapshot.child("email").getValue());

                        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference("Users");
                        currentUser.child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot dataSnapshot = task.getResult();
                                imageOfCurrentUser = String.valueOf(dataSnapshot.child("image").getValue());

                                Intent intent = new Intent(getApplication(), chatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra("name_of_roomate" , userName)
                                        .putExtra("username_of_roomate", userUsername)
                                        .putExtra("dp_of_roomate", userImage)
                                        .putExtra("email_of_roomate", userEmail)
                                        .putExtra("dp_of_user", imageOfCurrentUser);

                                startActivity(intent);
                                finish();

                            }
                        });

                    }
                });
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), UserLogs.class));
                finish();
            }
        });



    }
}