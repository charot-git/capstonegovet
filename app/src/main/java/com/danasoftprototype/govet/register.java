package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    EditText username, pass, confpass, emailreg, mobilenum;
    MaterialButton registeruser;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registeruser = (MaterialButton) findViewById(R.id.register);

        registeruser.setOnClickListener(this::registeruser);

        username = (EditText) findViewById(R.id.usernamereg);
        pass = (EditText) findViewById(R.id.passwordreg);
        emailreg = (EditText) findViewById(R.id.emailreg);
        confpass = (EditText) findViewById(R.id.repass);
        mobilenum = (EditText) findViewById(R.id.numberreg);





    }


    private void registeruser(View v) {

        String user = username.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String repass = confpass.getText().toString().trim();
        String email = emailreg.getText().toString().trim();
        String number = mobilenum.getText().toString().trim();

        if (user.isEmpty()) {
            username.setError("Username is required");
            username.requestFocus();
            return;
        }
        else if (email.isEmpty()) {
            emailreg.setError("E-mail is required");
            emailreg.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailreg.setError("E-mail is invalid");
            emailreg.requestFocus();
            return;
        }
        else if (number.isEmpty()) {
            mobilenum.setError("Mobile number is required");
            mobilenum.requestFocus();
            return;
        }
        else if (!Patterns.PHONE.matcher(number).matches()) {
            mobilenum.setError("Mobile number is invalid");
            mobilenum.requestFocus();
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

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(register.this, "User has been created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(register.this,  govethome.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(register.this, "Registration failed "+ task.getException(), Toast.LENGTH_LONG).show();
                        username.requestFocus();
                    }
                }
            });

        }





        /*{mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username, email, number);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isComplete()) {
                                                    Toast.makeText(register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(register.this, loginpage.class));
                                                } else {
                                                    Toast.makeText(register.this, "Registration failed, please try again!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });


                            }
                        }
                    });

        }}*/


    }
}