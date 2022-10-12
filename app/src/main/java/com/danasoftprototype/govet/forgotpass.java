package com.danasoftprototype.govet;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpass extends AppCompatActivity {

    EditText email;
    FirebaseAuth mAuth;
    Button reset;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        email = (EditText) findViewById(R.id.emailad);
        reset = (Button)  findViewById(R.id.reset);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = email.getText().toString();

                if (TextUtils.isEmpty(useremail)){
                    Toast.makeText(forgotpass.this, "Please enter a valid Email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else{

                bar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgotpass.this, "Please check your email for instructions", Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(forgotpass.this, "Failed to send password reset", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            }
        });

    }
}