package com.danasoftprototype.govet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.danasoftprototype.govet.FrontEnd.govethome;
import com.danasoftprototype.govet.startUp.loginpage;
import com.danasoftprototype.govet.startUp.register;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MaterialButton loginbtnmain = (MaterialButton) findViewById(R.id.loginbtnmain);
        MaterialButton registermain = (MaterialButton) findViewById(R.id.registermain);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplication(), govethome.class));
            finish();
        }

        loginbtnmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, loginpage.class));
            }
        });

        registermain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, register.class));
            }
        });


    }
}