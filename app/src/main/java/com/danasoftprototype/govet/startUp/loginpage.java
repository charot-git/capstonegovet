package com.danasoftprototype.govet.startUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEndVet.govethome3;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityLoginpageBinding;
import com.danasoftprototype.govet.FrontEndAdmin.govethome2;
import com.danasoftprototype.govet.settings.forgotpass;
import com.danasoftprototype.govet.FrontEnd.govethome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class loginpage extends AppCompatActivity {

    ActivityLoginpageBinding binding;


    //initialize variables across loginpage
    EditText email;
    EditText password;
    TextView forgotpassword;
    FirebaseAuth mAuth;
    MaterialButton userlogin;
    ImageView secret;

    //for developing only
    MaterialButton adminButton, userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        ProgressBar progressBar = (ProgressBar) findViewById((R.id.progressBar));
        email = findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        forgotpassword = (TextView) findViewById(R.id.forgotpass);

        //for developing
        adminButton = findViewById(R.id.loginbtnAdmin);
        userButton = findViewById(R.id.loginBtnVet);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminListener();
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vetListener();
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplication(), govethome.class));
            finish();
        }

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginpage.this, forgotpass.class);
                startActivity(intent);
            }
        });


        //special access for devs if cant login, limited access and features, will be removed once the project is done
        secret = (ImageView) findViewById(R.id.govetlogo3);



        userlogin = findViewById(R.id.loginbtn);
        mAuth = FirebaseAuth.getInstance();
        userlogin.setOnClickListener(this::userlogin);




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
            mAuth.signInWithEmailAndPassword(useremail, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(loginpage.this,"Login failed ", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
            });
            }


        }

    public void adminListener(){
        mAuth.signInWithEmailAndPassword("admingovet@gmail.com", "admingovet").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (mAuth != null){
                        for (UserInfo profile : mAuth.getCurrentUser().getProviderData()){
                            String email1 = profile.getEmail();
                            Toast.makeText(loginpage.this,"Welcome to GoVet  admin!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(loginpage.this,"Login failed", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplication(), govethome2.class);
                    startActivity(intent);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginpage.this,"Login failed ", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            }
        });

    }

    public void vetListener(){
        mAuth.signInWithEmailAndPassword("vetgovet@gmail.com", "vetgovet").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (mAuth != null){
                        for (UserInfo profile : mAuth.getCurrentUser().getProviderData()){
                            String email1 = profile.getEmail();
                            Toast.makeText(loginpage.this,"Welcome to GoVet  Doc!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(loginpage.this,"Login failed", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplication(), govethome3.class);
                    startActivity(intent);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginpage.this,"Login failed ", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            }
        });
    }

    private void ifUserIsAdmin() {

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admingovet@gmail.com")) {
            startActivity(new Intent(getApplication(), govethome2.class));
            finish();

        }
    }

    private void ifUserIsVet() {
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("vetgovet@gmail.com")) {
            startActivity(new Intent(getApplication(), govethome3.class));
            finish();

        }
    }
    }