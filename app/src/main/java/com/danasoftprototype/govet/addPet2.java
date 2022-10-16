package com.danasoftprototype.govet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEnd.profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class addPet2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView petImage2 , back;
    String petProfilePic2;
    String breed2;
    String speciesAdapter2;
    FrameLayout container2;
    RelativeLayout addpetcontainer2;
    Spinner speciesSpinner2, breedSpinner2;
    EditText petName2, petAge2;
    TextView petBirthday2;
    Button button2;
    StorageReference storageReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet2);

        container2 = findViewById(R.id.container);
        addpetcontainer2 = findViewById(R.id.addpetcontainer);

        speciesSpinner2 = findViewById(R.id.species1);
        petName2 = findViewById(R.id.petName);
        petBirthday2 = findViewById(R.id.inputbirthday);
        petAge2 = findViewById(R.id.petAge);
        breedSpinner2 = findViewById(R.id.inputbreed);
        petImage2 = findViewById(R.id.petpic);
        button2 = findViewById(R.id.button);
        storageReference2 = FirebaseStorage.getInstance().getReference();


        getSpeciesSpinnerAdapter();
        petbirthday();
        getPetPic();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillUpChecker();
            }
        });



    }

    private void fillUpChecker() {
        String name2 = petName2.getText().toString();
        String age2 = petAge2.getText().toString();
        String date2 = petBirthday2.getText().toString();

        if(speciesAdapter2.isEmpty()){
            Toast.makeText(this, "Please choose pet specie", Toast.LENGTH_SHORT).show();
        }
        else if(name2.isEmpty()){
            petName2.setError("Please input pet name");
            petName2.requestFocus();
        }else if(date2.isEmpty()){
            petBirthday2.setError("Please input pet name");
            petBirthday2.requestFocus();
        }else if(age2.isEmpty()){
            petAge2.setError("Please input pet name");
            petAge2.requestFocus();
        }
        else if(breed2.isEmpty()){
            Toast.makeText(this, "Please choose pet breed", Toast.LENGTH_SHORT).show();
        }
        else{

            FirebaseDatabase
                    .getInstance()
                    .getReference("user/pet/" + FirebaseAuth
                            .getInstance()
                            .getCurrentUser()
                            .getUid() + "/pet2").setValue(new Pet(speciesAdapter2, name2, age2, breed2, date2, petProfilePic2)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(addPet2.this, "Pet "+ name2 + " successfully added", Toast.LENGTH_SHORT).show();
                            container2.setVisibility(View.VISIBLE);
                            addpetcontainer2.setVisibility(View.GONE);
                            profile fragment = new profile();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, fragment);
                            transaction.commit();
                        }
                    });
        }

    }




    private void getPetPic() {
        petImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Uri imageUri2 = data.getData();
                upload(imageUri2);

            }
        }
    }

    private void upload(Uri imageUri) {
        StorageReference fileRef = storageReference2.child("images/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "petProfile2.jpg");


        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addPet2.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();


                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(petImage2);
                        String test = uri.toString();
                        updateProfilePic(test);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addPet2.this, "Profile picture update has failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfilePic(String url) {
        FirebaseDatabase.getInstance().getReference("user/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/petProfile2").setValue(url);
        petProfilePic2 = url;
    }


    private void getSpeciesSpinnerAdapter() {
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.species, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        speciesSpinner2.setAdapter(adapter);

        speciesSpinner2.setOnItemSelectedListener(this);
    }


    private void petbirthday() {

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        petBirthday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(addPet2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        petBirthday2.setText(date);

                    }
                },year,month,day);
                dialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String species = speciesSpinner2.getItemAtPosition(position).toString();
        speciesAdapter2 = species;

        //breedSpinner
        List<String> dogBreed = Arrays.asList("German Sheperd" , "Bulldog" , "Labrador Retriever" , "Golden Retriever");
        List<String> catBreed = Arrays.asList("Siamese Cat" , "British Shorthair" , "Maine Coon" , "Persian Cat");
        List<String> fishBreed = Arrays.asList("Koi" , "Shubunkin" , "Gold fish" , "Orana");
        List<String> hamsterBreed = Arrays.asList("Dwarf Roborovski" , "Campbell’s Dwarf Russian" , "Syrian (Golden) Hamster" , "Dwarf Winter White Russian");

        String spec = speciesSpinner2.getItemAtPosition(position).toString();
        if (spec.equals("Dog")) {
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dogBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner2.setAdapter(adapter);

            breedSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner2.getSelectedItem().toString();
                    breed2 = test;
                    button2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else if (spec.equals("Cat")){

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, catBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner2.setAdapter(adapter);

            breedSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner2.getSelectedItem().toString();
                    breed2 = test;
                    button2.setVisibility(View.VISIBLE);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else if (spec.equals("Fish")){
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, fishBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner2.setAdapter(adapter);

            breedSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner2.getSelectedItem().toString();
                    breed2 = test;
                    button2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else if (spec.equals("Hamster")){
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hamsterBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner2.setAdapter(adapter);

            breedSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner2.getSelectedItem().toString();
                    breed2 = test;
                    button2.setVisibility(View.VISIBLE);
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
}