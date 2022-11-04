package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.AdapterUsers;
import com.danasoftprototype.govet.ModelUser;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ModelUser> userList;
    TextView test;
    AdapterUsers adapterUsers;
    SearchView searchView;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        back = findViewById(R.id.back);

        recyclerView = findViewById(R.id.user_recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        searchView = findViewById(R.id.search_bar);

        userList = new ArrayList<>();

        getAllUsers();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void filter(String newText) {
        List<ModelUser> filteredList = new ArrayList<>();
        for (ModelUser item :userList){
            if (item.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapterUsers.filteredList(filteredList);
    }

    private void getAllUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    ModelUser modelUser = ds.getValue(ModelUser.class);
                    if(!modelUser.getUid().equals(firebaseUser.getUid())){
                        userList.add(modelUser);
                    }

                    adapterUsers = new AdapterUsers(getApplication(), userList);
                    recyclerView.setAdapter(adapterUsers);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}