package com.danasoftprototype.govet.FrontEnd;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sched extends Fragment {

    private TextView dateText, timeText, appointmentsText;
    DatabaseReference reference;
    CardView appointmentCard;
    public sched() {
    }

    public static sched newInstance(String param1, String param2) {
        sched fragment = new sched();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sched, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateText = view.findViewById(R.id.dateText);
        timeText = view.findViewById(R.id.timeText);
        appointmentsText = view.findViewById(R.id.Appointments);
        appointmentCard = view.findViewById(R.id.appointmentcard);

        appointmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), appointmentDetails.class));
            }
        });

        getBookData();
    }

    private void getBookData() {
        String booking = "bookingDetails";
        reference =FirebaseDatabase.getInstance().getReference("/Bookings/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(booking).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        appointmentCard.setVisibility(View.VISIBLE);
                        DataSnapshot dataSnapshot = task.getResult();
                        String date = String.valueOf(dataSnapshot.child("date").getValue());
                        String time = String.valueOf(dataSnapshot.child("time").getValue());
                        dateText.setText("DATE : " +date);
                        timeText.setText("TIME : "+time);

                    }
                    else {
                        appointmentsText.setText("No appointments yet");
                        appointmentCard.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }
}