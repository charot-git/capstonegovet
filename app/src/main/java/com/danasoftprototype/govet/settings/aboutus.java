package com.danasoftprototype.govet.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;

public class aboutus extends AppCompatActivity {

    ImageView back;
    TextView allen,dale,andrei,nica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        back = findViewById(R.id.back);
        allen = findViewById(R.id.name3);
        dale = findViewById(R.id.name1);
        andrei = findViewById(R.id.name2);
        nica = findViewById(R.id.name4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}