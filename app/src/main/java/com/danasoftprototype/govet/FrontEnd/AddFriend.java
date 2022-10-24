package com.danasoftprototype.govet.FrontEnd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityAddFriendBinding;
import com.danasoftprototype.govet.databinding.ActivityGovethomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class AddFriend extends AppCompatActivity {

    ImageView profilePic, pet1, pet2,pet3;
    TextView TextName, TextUsername;
    Button addFriend, cancelFriend;
    String email, image, name, uid, username;

    private ActivityAddFriendBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = ActivityAddFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);


        profilePic = binding.UserPic;
        pet1 = binding.addPetsPic1;
        pet2 = binding.addPetsPic2;
        pet3 = binding.addPetsPic3;
        TextName = binding.userName;
        TextUsername = binding.userUsername;
        addFriend = binding.addFriendButton;
        cancelFriend = binding.cancelButton;

        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");
        username = getIntent().getStringExtra("username");

        Picasso.get().load(image).placeholder(R.drawable.logogv).into(profilePic);

        TextName.setText(name);
        TextUsername.setText(username);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Pets/"+uid+"/Pet");
        reference.child("Pet1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());
                        Picasso.get().load(imgUrl).placeholder(R.drawable.logogv).into(pet1);

                    }
                }

            }
        });
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("/Pets/"+uid+"/Pet");
        reference.child("Pet2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());
                        Picasso.get().load(imgUrl).placeholder(R.drawable.logogv).into(pet2);

                    }
                }

            }
        });
        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("/Pets/"+uid+"/Pet");
        reference.child("Pet3").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());
                        Picasso.get().load(imgUrl).placeholder(R.drawable.logogv).into(pet3);

                    }
                }

            }
        });



    }
}
