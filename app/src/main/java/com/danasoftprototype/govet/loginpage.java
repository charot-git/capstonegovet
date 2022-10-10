package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

public class loginpage extends AppCompatActivity {


    //initialize variables across loginpage
    EditText email;
    EditText password;
    FirebaseAuth mAuth;
    MaterialButton userlogin;
    ImageView secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        ProgressBar progressBar = (ProgressBar) findViewById((R.id.progressBar));
        email = findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);


        //special access for devs if cant login, limited access and features, will be removed once the project is done
        secret = (ImageView) findViewById(R.id.govetlogo3);



        userlogin = findViewById(R.id.loginbtn);
        mAuth = FirebaseAuth.getInstance();
        userlogin.setOnClickListener(this::userlogin);

        //special access activity
        secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginpage.this, govethome.class);
                startActivity(intent);
            }
        });

    }

    private void userlogin(View view) {
        //get user input and store to string
        String useremail  = email.getText().toString();
        String userpass  = password.getText().toString();

        if(useremail.isEmpty()){
            email.setError("Please enter your email!");
            email.requestFocus();
            return;
        }
        else if (userpass.isEmpty()){
            password.setError("Please enter your password!");
            password.requestFocus();
            return;
        }
        else {
            //login process
            mAuth.signInWithEmailAndPassword(useremail,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (mAuth != null){
                            for (UserInfo profile : mAuth.getCurrentUser().getProviderData()){
                                String email1 = profile.getEmail();
                                Toast.makeText(loginpage.this,"Welcome to GoVet " + email1 + "!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Intent intent = new Intent(loginpage.this, govethome.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(loginpage.this,"Login failed " + task.getException(), Toast.LENGTH_SHORT).show();
                        email.requestFocus();

                    }
                }
            });

        }

    }
}