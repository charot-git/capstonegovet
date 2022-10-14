package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.User;
import com.danasoftprototype.govet.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class chatActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityChatBinding binding;
    private RecyclerView recyclerView;
    private EditText edtMessageInput;
    private TextView txtChattingWith;
    private ProgressBar progressBar;
    private ImageView imgToolbar;
    private ImageView imgSend;

    private MessageAdapter messageAdapter;

    private ArrayList<Message> messages;

    String usernameOfTheRoomate, emailOfRoomate, chatRoomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        usernameOfTheRoomate = getIntent().getStringExtra("username_of_roomate");
        emailOfRoomate = getIntent().getStringExtra("email_of_roomate");

        recyclerView = findViewById(R.id.recyclerMessages);
        edtMessageInput = findViewById(R.id.edtText);
        txtChattingWith = findViewById(R.id.ChattingWith);
        progressBar = findViewById(R.id.progressMessages);
        imgToolbar = findViewById(R.id.img_toolbar);
        imgSend = findViewById(R.id.sendMessageButton);


        txtChattingWith.setText(usernameOfTheRoomate);

        messages = new ArrayList<>();

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseDatabase.getInstance().getReference("messages/" + chatRoomID).push().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(), emailOfRoomate, edtMessageInput.getText().toString()));
                FirebaseDatabase.getInstance().getReference("messages/" + chatRoomID).push().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),emailOfRoomate,edtMessageInput.getText().toString()));
                edtMessageInput.setText("");
            }
        });

        messageAdapter = new MessageAdapter(messages, getIntent().getStringExtra("my_img"), getIntent().getStringExtra("img_of_roomate"), chatActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);



        setUpChatRoom();

    }

    private void setUpChatRoom(){
        FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUsername = snapshot.getValue(User.class).getUsername();
                if (usernameOfTheRoomate.compareTo(myUsername)>0){
                    chatRoomID = myUsername + usernameOfTheRoomate;
                }
                else if(usernameOfTheRoomate.compareTo(myUsername) == 0){
                    chatRoomID = myUsername + usernameOfTheRoomate;
                }
                else{
                    chatRoomID = usernameOfTheRoomate = myUsername;
                }
                attachMessageListener(chatRoomID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void attachMessageListener(String chatRoomID){
        FirebaseDatabase.getInstance().getReference("messages/" + chatRoomID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
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