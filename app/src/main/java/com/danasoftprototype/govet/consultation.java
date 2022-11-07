package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class consultation extends AppCompatActivity {

    String userName, userUsername, userImage, userEmail, imageOfCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child("6UAPpqZQCPUSlxaLYcWNOBq4pnm1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();

                userName = String.valueOf(dataSnapshot.child("name").getValue());
                userUsername = String.valueOf(dataSnapshot.child("username").getValue());
                userImage = String.valueOf(dataSnapshot.child("image").getValue());
                userEmail = String.valueOf(dataSnapshot.child("email").getValue());

                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference("Users");
                currentUser.child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot = task.getResult();
                        imageOfCurrentUser = String.valueOf(dataSnapshot.child("image").getValue());

                        Intent intent = new Intent(getApplication(), chatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("name_of_roomate" , userName)
                                .putExtra("username_of_roomate", userUsername)
                                .putExtra("dp_of_roomate", userImage)
                                .putExtra("email_of_roomate", userEmail)
                                .putExtra("dp_of_user", imageOfCurrentUser);

                        startActivity(intent);
                        finish();

                    }
                });

            }
        });

    }
}