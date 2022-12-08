package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEnd.profileupdate;
import com.danasoftprototype.govet.databinding.ActivityChatBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    ImageView back , info, send, dp , attach;
    TextView name, userName;
    EditText message;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    String nameOfRoomate, usernameOfRoomate,dpOfRoomate , chatRoomID,email, mydp;
    private static final int GALLERY_PICK = 1;


    MessageAdapter messageAdapter;
    ArrayList<Message> messages;
    StorageReference storageReference;

    StorageReference mImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameOfRoomate = getIntent().getStringExtra("name_of_roomate");
        usernameOfRoomate = getIntent().getStringExtra("username_of_roomate");
        dpOfRoomate = getIntent().getStringExtra("dp_of_roomate");
        email = getIntent().getStringExtra("email_of_roomate");
        mydp = getIntent().getStringExtra("dp_of_user");

        recyclerView = binding.recyclerViewMessages;
        back = binding.back;
        info = binding.info;
        send = binding.send;
        name = binding.name;
        attach = binding.file;
        userName = binding.userName;
        message = binding.message;
        dp = binding.dp;
        progressBar = binding.progressBar2;
        messages = new ArrayList<>();
        name.setText(nameOfRoomate);
        userName.setText(usernameOfRoomate);
        Picasso.get().load(dpOfRoomate).placeholder(R.drawable.logogv).into(dp);
        storageReference = FirebaseStorage.getInstance().getReference();

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
        messageAdapter = new MessageAdapter(messages, mydp, dpOfRoomate, chatActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);


        setUpChatRoom();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_PICK);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            upload(imageUri);

        }
    }

    private void upload(Uri imageUri) {

        DatabaseReference user_message_push = FirebaseDatabase.getInstance().getReference("Messages").child(chatRoomID).push();

        final String push_id = user_message_push.getKey();

        StorageReference fileRef = storageReference.child("images/" + chatRoomID + "/" +push_id +  ".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String test = uri.toString();
                        FirebaseDatabase.getInstance().getReference("Messages")
                                .child(chatRoomID).push()
                                .setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(), email, test));
                        message.setText("");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chatActivity.this, "Upload has failed", Toast.LENGTH_SHORT).show();
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