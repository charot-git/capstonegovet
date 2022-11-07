package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEndVet.MonitoringAdapterVet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserLogs extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView back;
    List<Logs>logsList;
    LogsAdapter logsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logs);

        recyclerView = findViewById(R.id.user_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        logsList = new ArrayList<>();

        getAllLogs();





        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAllLogs() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = root.child("UserLogs").child(FirebaseAuth.getInstance().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                logsList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                        Logs logs = ds.getValue(Logs.class);
                        logsList.add(logs);
                }

                logsAdapter = new LogsAdapter(getApplication(), logsList);
                recyclerView.setAdapter(logsAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}