package com.danasoftprototype.govet.FrontEndVet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityVetMonitorUpdatePetBinding;
import com.google.android.material.textfield.TextInputEditText;


public class vet_monitor_update_pet extends AppCompatActivity {

    private ActivityVetMonitorUpdatePetBinding binding;

    TextView petname;
    TextInputEditText status, date, time;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVetMonitorUpdatePetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        petname = binding.monitorpet;
        status = binding.statusPet;
        date = binding.petDate;
        time = binding.petTime;
        update = binding.updatevetbut;

        String petName = getIntent().getStringExtra("pet");
        String petStatus = getIntent().getStringExtra("status");
        String petDate = getIntent().getStringExtra("date");
        String petTime = getIntent().getStringExtra("time");

        petname.setText("Pet name : " + petName);
        status.setHint("Status : " + petStatus);
        date.setHint("Date : " + petDate);
        time.setHint("Time : " + petTime);

    }
}