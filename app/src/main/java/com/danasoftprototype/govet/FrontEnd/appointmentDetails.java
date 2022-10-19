package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class appointmentDetails extends AppCompatActivity {

    DatabaseReference reference;
    TextView dateText, timeText;
    Button cancel;
    private AlertDialog.Builder builder;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        cancel = findViewById(R.id.cancelAppointment);
        builder = new AlertDialog.Builder(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMethod();
            }
        });

        getBookData();
    }

    private void cancelMethod() {

        builder.setMessage("Cancel your booking for " + date + time).setTitle("Appointment cancellation")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelDatabase();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(), "Appointment not cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Appointment cancellation");
        alertDialog.show();

    }

    private void cancelDatabase() {

        String booking = "bookings";
        reference = FirebaseDatabase.getInstance().getReference("/user/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(booking).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(appointmentDetails.this, "Appointment cancelled", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplication(), govethome.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(appointmentDetails.this, "Appointment cancellation failed", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(appointmentDetails.this, "Cancellation failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getBookData() {
        String booking = "bookings";
        reference = FirebaseDatabase.getInstance().getReference("/user/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(booking).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        date = String.valueOf(dataSnapshot.child("date").getValue());
                        time = String.valueOf(dataSnapshot.child("time").getValue());
                        dateText.setText("DATE : " +date);
                        timeText.setText("TIME : "+time);

                    }
                }
            }
        });
    }
}