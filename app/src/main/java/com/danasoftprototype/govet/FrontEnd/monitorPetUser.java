package com.danasoftprototype.govet.FrontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityMonitorPetUserBinding;
import com.squareup.picasso.Picasso;

public class monitorPetUser extends AppCompatActivity {

    ImageView dp,back;
    TextView name, breed, status, date, time;
    Button contact, history;

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
                Toast.makeText(monitorPetUser.this, "contact", Toast.LENGTH_SHORT).show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(monitorPetUser.this, "history", Toast.LENGTH_SHORT).show();
            }
        });



    }
}