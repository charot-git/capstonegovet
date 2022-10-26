package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class announce extends AppCompatActivity {

    EditText postText;
    Button post;
    DatabaseReference postRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        postText = findViewById(R.id.Post);
        post = findViewById(R.id.PostButton);

        postRef = FirebaseDatabase.getInstance().getReference("Posts");


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postThis();
            }
        });

    }

    private void postThis() {
        String postString = postText.getText().toString();

        HashMap<Object, String> hashMap = new HashMap<>();

        hashMap.put("post" , postString);

        postRef.child("adminPosts").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(announce.this, "Post Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplication(), govethome2.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}