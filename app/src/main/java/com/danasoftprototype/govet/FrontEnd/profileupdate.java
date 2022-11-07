package com.danasoftprototype.govet.FrontEnd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.addPet;
import com.danasoftprototype.govet.userFullName;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class profileupdate extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    EditText fnameUI, mnameUI, lnameUI, number;
    MaterialButton updatebutton, changepicbutton;
    ImageView profilepic, back;
    TextView birthday;
    FirebaseAuth mAuth;
    FirebaseUser user;
    StorageReference storageReference;
    String bday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);


        mAuth = FirebaseAuth.getInstance();

        updatebutton = (MaterialButton) findViewById(R.id.updatebutton);
        changepicbutton = (MaterialButton) findViewById(R.id.changepic);
        back = findViewById(R.id.back);

        //go to updateuser method onclick
        updatebutton.setOnClickListener(this::updateuser);

        fnameUI = (EditText) findViewById(R.id.firstname);
        mnameUI = (EditText) findViewById(R.id.middlename);
        lnameUI = (EditText) findViewById(R.id.lastname);
        birthday = (TextView) findViewById(R.id.bday);
        number = findViewById(R.id.number);
        profilepic = (ImageView) findViewById(R.id.profilepic);

        storageReference = FirebaseStorage.getInstance().getReference();

        //uploade to storage
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

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBirthday();
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getBirthday() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(profileupdate.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                birthday.setText(date);
                bday = date;

            }
        },year,month,day);
        dialog.show();
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
        HashMap<String, Object> result = new HashMap<>();
        result.put("image", url);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(FirebaseAuth.getInstance().getUid()).updateChildren(result);

        /*FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/userInfo/profilepic").setValue(url);*/

    }

    private void updateuser(View view) {

        //get user input for name update
        String firstname = fnameUI.getText().toString().trim();
        String middlename = mnameUI.getText().toString().trim();
        String lastname = lnameUI.getText().toString().trim();
        String numberUser = number.getText().toString().trim();

        //Firestore data
        //addDataToFirestore();

        //concatenated user name for nav display
        String name = firstname + " " + middlename + " " + lastname;





    /*setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dayPicked = String.valueOf(dayOfMonth);
                String yearPicked = String.valueOf(year);
                String monthPicked = String.valueOf(month+1);
                bday = dayPicked + "/" +monthPicked + "/" +yearPicked;
                birthdate.setText("Birthday : "+bday);

            }
        });*/

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
        else if (numberUser.isEmpty()){
            number.setError("Middle name is required");
            number.requestFocus();
            return;
        }
        else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            //profile update process
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, Object> result = new HashMap<>();
                                result.put("name", name);
                                result.put("phone" , numberUser);
                                result.put("birthday", bday);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                ref.child(FirebaseAuth.getInstance().getUid()).updateChildren(result);

                                Toast.makeText(profileupdate.this, "Profile update successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(profileupdate.this, govethome.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(profileupdate.this, "Profile update failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profileupdate.this, "Profile update failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

}