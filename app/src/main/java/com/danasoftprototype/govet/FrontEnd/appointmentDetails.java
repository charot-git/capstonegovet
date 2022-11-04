package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class appointmentDetails extends AppCompatActivity {

    DatabaseReference reference;
    TextView dateText, timeText;
    ImageView back;
    Button cancel;
    private AlertDialog.Builder builder;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        back = findViewById(R.id.back);
        cancel = findViewById(R.id.cancelAppointment);
        builder = new AlertDialog.Builder(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMethod();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        String booking = "bookingDetails";
        reference =FirebaseDatabase.getInstance().getReference("Bookings");
        reference.child(booking).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    child.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(getApplication(), govethome.class));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getBookData() {
        String booking = "bookingDetails";
        reference =FirebaseDatabase.getInstance().getReference("Bookings");
        reference.child(booking).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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