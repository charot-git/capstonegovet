package com.danasoftprototype.govet.FrontEnd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

public class chatActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityChatBinding binding;
    FirebaseUser mAuth;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);




    }
}