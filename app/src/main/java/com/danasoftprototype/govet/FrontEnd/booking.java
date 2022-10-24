package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.addPet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class booking extends AppCompatActivity {
    private CalendarView calendarView;
    private TimePicker timePicker;
    private Button book;
    private String time;
    private String date;
    private String description;
    String dayPicked;
    String yearPicked;
    String monthPicked;
    private TextView timeView, dateView;
    private EditText descriptionText;
    private  int t1Hour, t1Minute;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        calendarView = findViewById(R.id.calendar);
        timePicker = findViewById(R.id.timepicker);
        dateView = findViewById(R.id.date);
        timeView = findViewById(R.id.Time);
        descriptionText = findViewById(R.id.descriptionInput);
        book = findViewById(R.id.book);
        builder = new AlertDialog.Builder(this);

        timePicker.setIs24HourView(false);

        timePicker.setHour(8);
        timePicker.setMinute(30);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dayPicked = String.valueOf(dayOfMonth);
                yearPicked = String.valueOf(year);
                monthPicked = String.valueOf(month);
                date = dayPicked + "/" +monthPicked + "/" +yearPicked;
                dateView.setText(date);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBooking();
            }
        });
    }

    private void confirmBooking() {
        getTime();


        builder.setMessage("Confirm booking at " + date + " " +  time).setTitle("Booking Confirmation")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookDatabase();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(booking.this, "Booking cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Booking");
        alertDialog.show();

    }

    private void bookDatabase() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser Fuser = mAuth.getInstance().getCurrentUser();

        //database set up
        String email = Fuser.getEmail();
        String uid = Fuser.getUid();
        description = descriptionText.getText().toString();

        HashMap<Object, String> hashMap = new HashMap<>();

        hashMap.put("time", time);
        hashMap.put("day", dayPicked);
        hashMap.put("month", monthPicked);
        hashMap.put("year", yearPicked);
        hashMap.put("date", date);
        hashMap.put("description", description);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("Bookings");
        reference.child(uid).child("bookingDetails").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplication(), "You have set an appointment for " +date , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), govethome.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), "Pet not added", Toast.LENGTH_SHORT).show();
            }
        });




        /*FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/bookings/")
                .setValue(new Bookings(time,dayPicked,monthPicked, yearPicked, date)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(booking.this, "You have successfully booked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), govethome.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(booking.this, "Booking failed", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void getTime() {
        int hourGetTime = timePicker.getHour();
        int minuteGetTime = timePicker.getMinute();

        t1Hour = hourGetTime;
        t1Minute = minuteGetTime;


        if (t1Hour == 12 && t1Minute> 0) {
            time = String.format("%02d", t1Hour) + " : " + String.format("%02d", t1Minute) + "PM";
            timeView.setText(time);
        } else if (t1Hour ==0) {
            time = String.format("%02d", t1Hour + 12) + " : " + String.format("%02d", t1Minute) + "AM";
            timeView.setText(time);
        }else if (t1Hour > 12) {
            time = String.format("%02d", t1Hour - 12) + " : " + String.format("%02d", t1Minute) + "PM";
            timeView.setText(time);
        } else {
            time = String.format("%02d", t1Hour) + " : " + String.format("%02d", t1Minute) + "AM";
            timeView.setText(time);
        }
    }
}