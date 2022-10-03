package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class profileupdate extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    EditText fnameUI, mnameUI, lnameUI;
    MaterialButton updatebutton;



    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);

        mAuth = FirebaseAuth.getInstance();

        updatebutton = (MaterialButton) findViewById(R.id.updatebutton);
        
        updatebutton.setOnClickListener(this::updateuser);

        fnameUI = (EditText) findViewById(R.id.firstname);
        mnameUI = (EditText) findViewById(R.id.middlename);
        lnameUI = (EditText) findViewById(R.id.lastname);

        



    }

    private void updateuser(View view) {
        String firstname = fnameUI.getText().toString().trim();
        String middlename = mnameUI.getText().toString().trim();
        String lastname = lnameUI.getText().toString().trim();

        String name = firstname + " " + middlename + " " + lastname;

        if (firstname.isEmpty()) {
            fnameUI.setError("First name is required");
            fnameUI.requestFocus();
            return;
        }
        else if (middlename.isEmpty()) {
            fnameUI.setError("Middle name is required");
            fnameUI.requestFocus();
            return;
        }else if (lastname.isEmpty()) {
            lnameUI.setError("Middle name is required");
            lnameUI.requestFocus();
            return;
        }
        else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(profileupdate.this, govethome.class);

                                Log.d(TAG, "User profile updated.");

                                startActivity(intent);
                            }
                        }
                    });
        }

    }
}