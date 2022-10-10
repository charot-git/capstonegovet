package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {


    //initialization of all variables across register form
    EditText pass, confpass, emailreg;
    MaterialButton registeruser;
    ProgressBar progressBar;
    String TAG;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registeruser = (MaterialButton) findViewById(R.id.register);

        //go to registeruser method onclick
        registeruser.setOnClickListener(this::registeruser);

        //get valiue from user by id in xml
        pass = (EditText) findViewById(R.id.passwordreg);
        emailreg = (EditText) findViewById(R.id.emailreg);
        confpass = (EditText) findViewById(R.id.repass);

        progressBar = (ProgressBar) findViewById(R.id.registerprogress);





    }


    private void registeruser(View v) {

        //get user input and put to string
        String password = pass.getText().toString().trim();
        String repass = confpass.getText().toString().trim();
        String email = emailreg.getText().toString().trim();


        //EditText Checker
        if (email.isEmpty()) {
            emailreg.setError("E-mail is required");
            emailreg.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailreg.setError("E-mail is invalid");
            emailreg.requestFocus();
            return;
        }
        else if (password.isEmpty()) {
            pass.setError("Password is required!");
            pass.requestFocus();
            return;
        } else if (password.length() < 6) {
            pass.setError("Password should be 6 characters or longer");
            pass.requestFocus();
            return;
        } else if (repass.isEmpty()) {
            confpass.setError("Password confirmation is required!");
            confpass.requestFocus();
            return;
        } else if (!repass.equals(password)) {
            Toast.makeText(register.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        } else {


            progressBar.setVisibility(View.VISIBLE);

            //registration process via Email and Password
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);

                        //send email verification
                        FirebaseUser Fuser = mAuth.getInstance().getCurrentUser();
                        Fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(register.this, "Verification Email has been sent", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure : Email not sent " + e.getMessage());

                            }
                        });
                        
                        Toast.makeText(register.this, "User has been created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(register.this,  profileupdate.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(register.this, "Registration failed "+ task.getException(), Toast.LENGTH_LONG).show();
                        emailreg.requestFocus();
                    }
                }
            });



        }




    }
}