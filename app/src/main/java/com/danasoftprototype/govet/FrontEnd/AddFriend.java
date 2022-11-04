package com.danasoftprototype.govet.FrontEnd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.databinding.ActivityAddFriendBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class AddFriend extends AppCompatActivity {

    ImageView profilePic, pet1, pet2,pet3, back;
    TextView TextName, TextUsername;
    Button addFriend, cancelFriend;
    String CurrentState = "not_friends";
    String email, image, name, uid, username;
    DatabaseReference mUser, requestRef, friendRef;
    ProgressBar progressBar;

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
        back = binding.back;
        TextName = binding.userName;
        TextUsername = binding.userUsername;
        addFriend = binding.addFriendButton;
        cancelFriend = binding.cancelButton;
        progressBar = binding.progressBarAddFriend;


        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");
        username = getIntent().getStringExtra("username");

        Picasso.get().load(image).placeholder(R.drawable.logogv).into(profilePic);

        TextName.setText(name);
        TextUsername.setText(username);

        mUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        requestRef = FirebaseDatabase.getInstance().getReference().child("Request");
        friendRef = FirebaseDatabase.getInstance().getReference().child("Friends");

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

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendMethod(uid);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void addFriendMethod(String uid) {
        progressBar.setVisibility(View.VISIBLE);
        addFriend.setEnabled(false);
        if(CurrentState.equals("not_friends")){
            SendFriendRequest();
        }
        else if (CurrentState.equals("request_sent")){
            addFriend.setText("Cancel Friend Request");
            CancelFriendRequest();
        }
        else if (CurrentState.equals("request_cancelled")){
            SendFriendRequest();
        }

    }

    private void SendFriendRequest() {
        requestRef.child(FirebaseAuth.getInstance().getUid()).child(uid).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    friendRef.child(uid).child(FirebaseAuth.getInstance().getUid()).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                addFriend.setEnabled(true);
                                CurrentState = "request_sent";
                                addFriend.setText("Cancel Friend Request");
                                cancelFriend.setVisibility(View.INVISIBLE);
                                cancelFriend.setEnabled(false);
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }

            }
        });
    }

    private void CancelFriendRequest(){
        requestRef.child(FirebaseAuth.getInstance().getUid()).child(uid).child("request_type").setValue("removed").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                friendRef.child(uid).child(FirebaseAuth.getInstance().getUid()).child("request_type").setValue("removed").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        addFriend.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        CurrentState = "request_cancelled";
                        addFriend.setText("Add Friend");
                    }
                });

            }
        });

    }

    private void ConfirmFriendRequest(){

    }


}
