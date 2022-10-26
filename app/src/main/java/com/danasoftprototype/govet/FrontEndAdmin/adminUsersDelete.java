package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.squareup.picasso.Picasso;

public class adminUsersDelete extends AppCompatActivity {
    TextView name, email;
    Button delete;
    ImageView userPicUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_delete);

        name = findViewById(R.id.userText);
        email = findViewById(R.id.emailText);
        delete = findViewById(R.id.button4);
        userPicUser = findViewById(R.id.userPicMon);

        String nameUser = getIntent().getStringExtra("name");
        String emailUser = getIntent().getStringExtra("email");
        String userPic = getIntent().getStringExtra("image");

        name.setText(nameUser);
        email.setText(emailUser);

        Picasso.get().load(userPic).error(R.drawable.logogv).placeholder(R.drawable.logogv).into(userPicUser);

    }
}