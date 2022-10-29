package com.danasoftprototype.govet.FrontEndVet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.danasoftprototype.govet.databinding.ActivityVetMonitorUpdatePetBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class vet_monitor_update_pet extends AppCompatActivity {

    private ActivityVetMonitorUpdatePetBinding binding;

    TextView petname, statusPet, datePet, timePet;
    EditText status;
    CalendarView calendarView;
    TimePicker timePicker;
    Button update;


    String uid;
    String dayPicked;
    String yearPicked;
    String monthPicked;
    String date;
    String statusOfPet = "Admitted";
    private String time;
    private  int t1Hour, t1Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVetMonitorUpdatePetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        petname = binding.monitorpet;
        status = binding.statusPet;
        update = binding.updatevetbut;
        statusPet = binding.monitorStatus;
        datePet = binding.monitorDatetxt;
        timePet = binding.monitorTimetxt;
        calendarView = binding.monitorCalendar;
        timePicker = binding.monitorTime;


        String petName = getIntent().getStringExtra("pet");
        String petStatus = getIntent().getStringExtra("status");
        String petDate = getIntent().getStringExtra("date");
        String petTime = getIntent().getStringExtra("time");
        uid = getIntent().getStringExtra("uid");

        petname.setText("Pet name : " + petName);
        statusPet.setText("Status : " + petStatus);
        datePet.setText("Date : " + petDate);
        timePet.setText("Time : " + petTime);

        date = petDate;
        time = petTime;
        statusOfPet = petStatus;

        getDate();
        getTime();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });


    }


    private void getDate() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dayPicked = String.valueOf(dayOfMonth);
                yearPicked = String.valueOf(year);
                monthPicked = String.valueOf(month);
                date = dayPicked + "/" +monthPicked + "/" +yearPicked;
                datePet.setText("Date : " + date);
            }
        });

    }

    private void getTime() {


        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                int hourGetTime = timePicker.getHour();
                int minuteGetTime = timePicker.getMinute();

                t1Hour = hourGetTime;
                t1Minute = minuteGetTime;

                if (t1Hour == 12 && t1Minute> 0) {
                    time = String.format("%02d", t1Hour) + " : " + String.format("%02d", t1Minute) + " PM";
                    timePet.setText("Time : " + time);
                } else if (t1Hour ==0) {
                    time = String.format("%02d", t1Hour + 12) + " : " + String.format("%02d", t1Minute) + " AM";
                    timePet.setText("Time : " + time);
                }else if (t1Hour > 12) {
                    time = String.format("%02d", t1Hour - 12) + " : " + String.format("%02d", t1Minute) + " PM";
                    timePet.setText("Time : " + time);
                } else {
                    time = String.format("%02d", t1Hour) + " : " + String.format("%02d", t1Minute) + " AM";
                    timePet.setText("Time : " + time);
                }

            }
        });
    }

    private void updateDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("status" , statusOfPet);
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uid).updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(vet_monitor_update_pet.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}