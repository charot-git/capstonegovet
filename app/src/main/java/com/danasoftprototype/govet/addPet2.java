package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEnd.govethome;
import com.danasoftprototype.govet.FrontEnd.profile;
import com.danasoftprototype.govet.FrontEnd.profileupdate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class addPet2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView petProfilePic;
    Spinner petSpecies, petAge;
    EditText petName, petBreed;
    Button submit;
    TextView petBirthday;
    StorageReference storageReference;
    public String age;
    public String name;
    public String bday;
    public String breed;
    public String species;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet2);

        storageReference = FirebaseStorage.getInstance().getReference();

        petProfilePic = findViewById(R.id.petPic);
        petSpecies = findViewById(R.id.petSpecies);
        petBreed = findViewById(R.id.petBreed);
        petBirthday = findViewById(R.id.petBirthday);
        petAge = findViewById(R.id.petAge);
        petName = findViewById(R.id.petName);
        submit = findViewById(R.id.button);



        //petProfilePicIntent
        petProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        //Spinner set up for species
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.species, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        petSpecies.setAdapter(adapter);
        petSpecies.setOnItemSelectedListener(this);

        //Spinner set up for age
        ArrayAdapter<CharSequence>adapterAge = ArrayAdapter.createFromResource(this,R.array.age, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        petAge.setAdapter(adapterAge);
        petAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //get name from user
        name = petName.getText().toString();

        //birthday dialogue
        petbirthday();


        //submit click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckerGetter();
            }
        });
    }

    //petProfilePicImgUri
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                petProfilePic.setImageURI(imageUri);

                upload(imageUri);
            }
        }
    }


    private void petbirthday() {

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        petBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(addPet2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        petBirthday.setText(date);
                        bday = date;

                    }
                },year,month,day);
                dialog.show();
            }
        });
    }
    //petProfilePicUploadStroage
    private void upload(Uri imageUri) {
        StorageReference fileRef = storageReference.child("images/pet2/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "pet2.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addPet2.this, "Pet profile picture upload success!", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(petProfilePic);
                        String picUrl = uri.toString();
                        url = picUrl;
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addPet2.this, "Pet profile picture upload has failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    //Species value on select and breed spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String petspecies = parent.getItemAtPosition(position).toString();
        species = petspecies;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //update database for pet 2
    private void CheckerGetter() {
        String petname = petName.getText().toString();
        String breedOfPet = petBreed.getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser Fuser = mAuth.getInstance().getCurrentUser();

        //database set up
        String email = Fuser.getEmail();
        String uid = Fuser.getUid();

        HashMap<Object, String> hashMap = new HashMap<>();

        hashMap.put("petAge", age.trim());
        hashMap.put("petBirthday", bday.trim());
        hashMap.put("petBreed", breedOfPet.trim());
        hashMap.put("petName", petname.trim());
        hashMap.put("petProfilePic", url);
        hashMap.put("petSpecies", species.trim());

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("Pets");
        reference.child(uid).child("Pet").child("Pet2").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(addPet2.this, petname +" has been added to your pets", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplication(), govethome.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addPet2.this, "Pet not added", Toast.LENGTH_SHORT).show();
            }
        });
    }

}