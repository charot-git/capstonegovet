package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.databinding.ActivityAppointmentConfirmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class appointment_confirm extends AppCompatActivity {

    ActivityAppointmentConfirmBinding binding;
    ImageView back;
    Button confirm;
    TextView name, description, time, date;

    String nameString;
    String dateString;
    String timeString;
    String descriptionString;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        back = binding.back;
        confirm = binding.confirm;
        name = binding.nameText;
        description = binding.DescriptionText;
        time = binding.timeText;
        date = binding.DateText;


        nameString = getIntent().getStringExtra("name");
        dateString = getIntent().getStringExtra("date");
        timeString = getIntent().getStringExtra("time");
        descriptionString = getIntent().getStringExtra("description");
        uid = getIntent().getStringExtra("uid");

        name.setText("Name : " + nameString);
        date.setText("Date : " + dateString);
        description.setText("Description : " + descriptionString);
        time.setText("Time : " + timeString);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAppointmentMethod();
            }
        });

    }

    private void finishAppointmentMethod() {
        //todo

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
        ref.child(uid).push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                updatedValues.put("name" , name);
                updatedValues.put("status" , "Finished Appointment");
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uid).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings").child("bookingDetails");
                        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child: snapshot.getChildren()) {
                                    child.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}