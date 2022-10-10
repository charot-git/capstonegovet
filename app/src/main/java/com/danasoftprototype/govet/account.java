package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class account extends AppCompatActivity {
    Button emailauth, mobileauth;
    String TAG;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        emailauth = (Button) findViewById(R.id.emailauth);
        mobileauth = (Button) findViewById(R.id.mobileauth);

        emailauth.setOnClickListener(this::emailauthmethod);

    }

    private void emailauthmethod(View view) {
        FirebaseUser Fuser = mAuth.getInstance().getCurrentUser();
        Fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(account.this, "Verification Email has been sent", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure : Email not sent " + e.getMessage());

            }
        });
    }
}