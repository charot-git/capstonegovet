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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class addPet3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView petProfilePic;
    Spinner petSpecies, petBreed, petAge;
    EditText petName;
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
        setContentView(R.layout.activity_add_pet3);

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
                DatePickerDialog dialog = new DatePickerDialog(addPet3.this, new DatePickerDialog.OnDateSetListener() {
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
        StorageReference fileRef = storageReference.child("images/pet3/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "pet3.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addPet3.this, "Pet profile picture upload success!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(addPet3.this, "Pet profile picture upload has failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    //Species value on select and breed spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String petspecies = parent.getItemAtPosition(position).toString();
        species = petspecies;

        //dog breed listener
        if (species.equals("Dog")){
            ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.dogBreed, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            petBreed.setAdapter(adapter);
            petBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String petbreed = parent.getItemAtPosition(position).toString();
                    breed = petbreed;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        //cat breed listener
        else if(species.equals("Cat")){
            ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.catBreed, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            petBreed.setAdapter(adapter);
            petBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String petbreed = parent.getItemAtPosition(position).toString();
                    breed = petbreed;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        //fish breed listener
        else if(species.equals("Fish")){
            ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.fishBreed, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            petBreed.setAdapter(adapter);
            petBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String petbreed = parent.getItemAtPosition(position).toString();
                    breed = petbreed;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        //hamster breed listener
        else if(species.equals("Hamster")){
            ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.hamsterBreed, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            petBreed.setAdapter(adapter);
            petBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String petbreed = parent.getItemAtPosition(position).toString();
                    breed = petbreed;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //update database for pet 2
    private void CheckerGetter() {
        String petname = petName.getText().toString();

        FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/pets/pet3")
                .setValue(new Pet(species.trim(),petname.trim(),age.trim(),breed.trim(),bday.trim(),url)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addPet3.this, "Successfully registered pet", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), govethome.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addPet3.this, "Failed registering pet", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}