package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.danasoftprototype.govet.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    ImageView back , info, send, dp;
    TextView name, userName;
    EditText message;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    String nameOfRoomate, usernameOfRoomate,dpOfRoomate , chatRoomID,email;


    MessageAdapter messageAdapter;
    ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameOfRoomate = getIntent().getStringExtra("name_of_roomate");
        usernameOfRoomate = getIntent().getStringExtra("username_of_roomate");
        dpOfRoomate = getIntent().getStringExtra("dp_of_roomate");
        email = getIntent().getStringExtra("email_of_roomate");
        String myDp = getIntent().getStringExtra("myDp");

        recyclerView = binding.recyclerViewMessages;
        back = binding.back;
        info = binding.info;
        send = binding.send;
        name = binding.name;
        userName = binding.userName;
        message = binding.message;
        dp = binding.dp;
        progressBar = binding.progressBar2;
        messages = new ArrayList<>();
        name.setText(nameOfRoomate);
        userName.setText(usernameOfRoomate);
        Picasso.get().load(dpOfRoomate).placeholder(R.drawable.logogv).into(dp);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageChecker = message.getText().toString();
                if (messageChecker.isEmpty()){
                    message.setError("Please enter a message");
                    message.requestFocus();
                }
                else {
                    FirebaseDatabase.getInstance().getReference("Messages")
                            .child(chatRoomID).push()
                            .setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(), email, message.getText().toString()));
                    message.setText("");
                }

            }
        });
        messageAdapter = new MessageAdapter(messages, myDp, dpOfRoomate, chatActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);


        setUpChatRoom();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpChatRoom(){
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myusername = snapshot.getValue(User.class).getUsername();
                if (usernameOfRoomate.compareTo(myusername)>0){
                    chatRoomID = myusername + usernameOfRoomate;
                }
                else if (usernameOfRoomate.compareTo(myusername) == 0){
                    chatRoomID = myusername + usernameOfRoomate;
                }
                else{
                    chatRoomID = usernameOfRoomate + myusername;
                }
                attachMessageListener(chatRoomID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attachMessageListener(String chatRoomID){
        FirebaseDatabase.getInstance().getReference("Messages").child(chatRoomID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size()-1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}