package com.danasoftprototype.govet.FrontEnd;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityMonitorPetUserBinding;
import com.squareup.picasso.Picasso;

public class monitorPetUser extends AppCompatActivity {

    ImageView dp;
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

        Picasso.get().load(petPic).placeholder(R.drawable.logogv).into(dp);

        name.setText(petName);
        breed.setText(petBreed);
        status.setText("Status : " +petStatus);
        date.setText("Date : " + petDate);
        time.setText("Time : " + petTime);


    }
}