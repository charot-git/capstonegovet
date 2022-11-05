package com.danasoftprototype.govet.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.changePassDialog;
import com.danasoftprototype.govet.mobileauthsetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class account extends AppCompatActivity {
    Button emailauth, mobileauth, changepass;
    String TAG;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        emailauth = (Button) findViewById(R.id.emailauth);
        mobileauth = (Button) findViewById(R.id.mobileauth);
        changepass = (Button) findViewById(R.id.changepass);

        emailauth.setOnClickListener(this::emailauthmethod);
        mobileauth.setOnClickListener(this::mobileauthmethod);
        changepass.setOnClickListener(this::changepassmethod);

    }

    private void changepassmethod(View view) {
        changePassDialog changePassDialog = new changePassDialog();

        changePassDialog.show(getSupportFragmentManager(), "Change Password Dialog");

    }




    private void mobileauthmethod(View view) {
        Intent intent = new Intent(account.this, mobileauthsetup.class);
        startActivity(intent);

    }

    private void emailauthmethod(View view) {
        FirebaseUser Fuser = FirebaseAuth.getInstance().getCurrentUser();
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