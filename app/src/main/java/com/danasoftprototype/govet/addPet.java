package com.danasoftprototype.govet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class addPet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String species;
    String name, age, breed;
    ImageView petImage;
    Spinner speciesSpinner, breedSpinner;
    EditText petName, petAge;
    TextView petBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        speciesSpinner = findViewById(R.id.species1);
        petName = findViewById(R.id.petName);
        petBirthday = findViewById(R.id.inputbirthday);
        petAge = findViewById(R.id.petAge);
        breedSpinner = findViewById(R.id.inputbreed);
        petImage = findViewById(R.id.petpic);

        getSpeciesSpinnerAdapter();
        getName();
        getAge();
        petbirthday();
        getBreed();
        getPetPic();



    }

    private void getPetPic() {
        petImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });
    }

    private void getBreed() {


    }

    private void getAge() {
        age = petAge.getText().toString();
    }

    private void getSpeciesSpinnerAdapter() {
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.species, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(adapter);

        speciesSpinner.setOnItemSelectedListener(this);
    }

    private void getName(){
        name = petName.getText().toString();

    }

    private void petbirthday() {

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        petBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(addPet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        petBirthday.setText(date);

                    }
                },year,month,day);
                dialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        species = speciesSpinner.getItemAtPosition(position).toString();

        //breedSpinner
        List<String> dogBreed = Arrays.asList("German Sheperd" , "Bulldog" , "Labrador Retriever" , "Golden Retriever");
        List<String> catBreed = Arrays.asList("Siamese Cat" , "British Shorthair" , "Maine Coon" , "Persian Cat");
        List<String> fishBreed = Arrays.asList("Koi" , "Shubunkin" , "Gold fish" , "Orana");
        List<String> hamsterBreed = Arrays.asList("Dwarf Roborovski" , "Campbell’s Dwarf Russian" , "Syrian (Golden) Hamster" , "Dwarf Winter White Russian");

        String spec = speciesSpinner.getItemAtPosition(position).toString();
        if (spec.equals("Dog")) {
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dogBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner.setAdapter(adapter);

            breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner.getSelectedItem().toString();
                    Toast.makeText(addPet.this, test, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else if (spec.equals("Cat")){

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, catBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner.setAdapter(adapter);

            breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner.getSelectedItem().toString();
                    Toast.makeText(addPet.this, test, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else if (spec.equals("Fish")){
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, fishBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner.setAdapter(adapter);

            breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner.getSelectedItem().toString();
                    Toast.makeText(addPet.this, test, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else if (spec.equals("Hamster")){
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hamsterBreed);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);

            breedSpinner.setAdapter(adapter);

            breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String test = breedSpinner.getSelectedItem().toString();
                    Toast.makeText(addPet.this, test, Toast.LENGTH_SHORT).show();
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