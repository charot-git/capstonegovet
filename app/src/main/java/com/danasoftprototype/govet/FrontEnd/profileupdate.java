package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class profileupdate extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    EditText fnameUI, mnameUI, lnameUI;
    MaterialButton updatebutton, changepicbutton;
    ImageView profilepic;
    FirebaseAuth mAuth;
    FirebaseUser user;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);


        mAuth = FirebaseAuth.getInstance();

        updatebutton = (MaterialButton) findViewById(R.id.updatebutton);
        changepicbutton = (MaterialButton) findViewById(R.id.changepic);

        //go to updateuser method onclick
        updatebutton.setOnClickListener(this::updateuser);

        fnameUI = (EditText) findViewById(R.id.firstname);
        mnameUI = (EditText) findViewById(R.id.middlename);
        lnameUI = (EditText) findViewById(R.id.lastname);
        profilepic = (ImageView) findViewById(R.id.profilepic);

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("images/" + mAuth.getCurrentUser().getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });


        //profile pic image on click listener
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        //change profile picture
        changepicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profilepic.setImageURI(imageUri);

                upload(imageUri);

            }
        }
    }

    private void upload(Uri imageUri) {


        StorageReference fileRef = storageReference.child("images/" + mAuth.getCurrentUser().getUid() + "profile.jpg");


        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(profileupdate.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();


                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilepic);
                        String test = uri.toString();
                        updateProfilePic(test);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profileupdate.this, "Profile picture update has failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateProfilePic(String url) {

        FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/userInfo/profilepic").setValue(url);

    }

    private void updateuser(View view) {

        //get user input for name update
        String firstname = fnameUI.getText().toString().trim();
        String middlename = mnameUI.getText().toString().trim();
        String lastname = lnameUI.getText().toString().trim();

        //Firestore data
        //addDataToFirestore();


        //concatenated user name for nav display
        String name = firstname + " " + middlename + " " + lastname;

        if (firstname.isEmpty()) {
            fnameUI.setError("First name is required");
            fnameUI.requestFocus();
            return;
        }
        else if (middlename.isEmpty()) {
            mnameUI.setError("Middle name is required");
            mnameUI.requestFocus();
            return;
        }else if (lastname.isEmpty()) {
            lnameUI.setError("Middle name is required");
            lnameUI.requestFocus();
            return;
        }
        else{


            //profile update process
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(profileupdate.this, "Profile update successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(profileupdate.this, govethome.class);
                                startActivity(intent);
                            }
                        }
                    });
        }


    }

}