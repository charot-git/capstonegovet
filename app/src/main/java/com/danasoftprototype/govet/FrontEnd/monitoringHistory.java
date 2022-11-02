package com.danasoftprototype.govet.FrontEnd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danasoftprototype.govet.MonitoringAdapter;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.petAdmitMonitoring;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class monitoringHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_history);
    }

    public static class monitorActivty extends AppCompatActivity {

        RecyclerView recyclerView;
        List<petAdmitMonitoring> petAdmitMonitoringList;
        MonitoringAdapter monitoringAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_monitor_activty);

            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

            petAdmitMonitoringList = new ArrayList<>();

            getAllAdmittedPets();

        }

        private void getAllAdmittedPets() {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    petAdmitMonitoringList.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        petAdmitMonitoring petAdmitMonitoring = ds.getValue(com.danasoftprototype.govet.petAdmitMonitoring.class);
                        petAdmitMonitoringList.add(petAdmitMonitoring);
                    }

                    monitoringAdapter = new MonitoringAdapter(getApplication(), petAdmitMonitoringList);
                    recyclerView.setAdapter(monitoringAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}