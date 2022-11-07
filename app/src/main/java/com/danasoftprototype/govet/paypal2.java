package com.danasoftprototype.govet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class paypal2 extends AppCompatActivity {

    Button login , signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal2);

        login = findViewById(R.id.loginbtnpaypal);
        signup = findViewById(R.id.registermain);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), paypal3.class));
                finish();
            }
        });

        String signUpUrl = "https://www.paypal.com/ph/webapps/mpp/account-selection";

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(signUpUrl));
                startActivity(i);
                finish();
            }
        });
    }
}