package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.databinding.ActivityAdmitPetAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class admitPetAdmin extends AppCompatActivity {

    TextView name;

    ImageView petpic1,petpic2, petpic3;

    CardView pet1, pet2, pet3;

    TextView petname1,petage1,petbreed1;
    TextView petname2,petage2,petbreed2;
    TextView petname3,petage3,petbreed3;

    String petName1, petBreed1, petDp1;
    String petName2, petBreed2, petDp2;
    String petName3, petBreed3, petDp3;


    DatabaseReference reference1, reference2, reference3;

    private ActivityAdmitPetAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmitPetAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nameUser = getIntent().getStringExtra("name");
        String uid = getIntent().getStringExtra("uid");

        name = binding.userName;
        name.setText(nameUser);

        pet1 = binding.pet1;
        pet2 = binding.pet2;
        pet3 = binding.pet3;

        pet1.setVisibility(View.INVISIBLE);
        pet2.setVisibility(View.INVISIBLE);
        pet3.setVisibility(View.INVISIBLE);

        petname1 = binding.petname1;
        petage1 = binding.year1;
        petbreed1 = binding.breed1;
        petpic1= binding.petPic1;

        petname2 = binding.petname2;
        petage2 = binding.year2;
        petbreed2 = binding.breed2;
        petpic2= binding.petPic2;

        petname3 = binding.petname3;
        petage3 = binding.year3;
        petbreed3 = binding.breed3;
        petpic3= binding.petPic3;

        pet1Show(uid);
        pet2Show(uid);
        pet3Show(uid);


    }

    private void pet1Show(String uid) {
        reference1 = FirebaseDatabase.getInstance().getReference("/Pets/" + uid+"/Pet");
        reference1.child("Pet1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("petName").getValue());
                        String species = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String breed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String age = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String birthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        pet1.setVisibility(View.VISIBLE);
                        petname1.setText(name);
                        petage1.setText(age);
                        petbreed1.setText(breed);
                        Picasso.get().load(imgUrl).into(petpic1);

                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admitThisPet1(uid);
            }
        });
        pet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admitThisPet2(uid);
            }
        });
        pet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admitThisPet3(uid);
            }
        });

    }
    private void pet2Show(String uid) {
        reference2 = FirebaseDatabase.getInstance().getReference("/Pets/" + uid+"/Pet");
        reference2.child("Pet2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("petName").getValue());
                        String species = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String breed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String age = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String birthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        pet2.setVisibility(View.VISIBLE);
                        petname2.setText(name);
                        petage2.setText(age);
                        petbreed2.setText(breed);
                        Picasso.get().load(imgUrl).into(petpic2);

                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void pet3Show(String uid) {
        reference3 = FirebaseDatabase.getInstance().getReference("/Pets/" + uid+"/Pet");
        reference3.child("Pet3").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("petName").getValue());
                        String species = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String breed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String age = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String birthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        pet3.setVisibility(View.VISIBLE);
                        petname3.setText(name);
                        petage3.setText(age);
                        petbreed3.setText(breed);
                        Picasso.get().load(imgUrl).into(petpic3);

                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void admitThisPet1(String uid) {
        reference1 = FirebaseDatabase.getInstance().getReference("/Pets/" + uid+"/Pet");
        reference1.child("Pet1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("petName").getValue());
                        String species = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String breed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String age = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String birthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        petName1 = name;
                        petBreed1 = breed;
                        petDp1 = imgUrl;

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm a");
                        String currentDate = formatter.format(new Date());
                        String currentTime = formatterTime.format(new Date());


                        HashMap<Object, String> hashMap = new HashMap<>();

                        hashMap.put("petName", petName1);
                        hashMap.put("breed", petBreed1);
                        hashMap.put("petPic", petDp1);
                        hashMap.put("date", currentDate);
                        hashMap.put("time", currentTime);
                        hashMap.put("status", "Admitted");


                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        DatabaseReference reference = database.getReference("Monitoring");
                        reference.child(uid).child("Pet1").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(admitPetAdmin.this, petName1 +" has been admitted", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void admitThisPet2(String uid) {
        reference2 = FirebaseDatabase.getInstance().getReference("/Pets/" + uid+"/Pet");
        reference2.child("Pet2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("petName").getValue());
                        String species = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String breed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String age = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String birthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        petName2 = name;
                        petBreed2 = breed;
                        petDp2 = imgUrl;

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm a");
                        String currentDate = formatter.format(new Date());
                        String currentTime = formatterTime.format(new Date());


                        HashMap<Object, String> hashMap = new HashMap<>();

                        hashMap.put("petName", petName2);
                        hashMap.put("breed", petBreed2);
                        hashMap.put("petPic", petDp2);
                        hashMap.put("date", currentDate);
                        hashMap.put("time", currentTime);
                        hashMap.put("status", "Admitted");


                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Monitoring");
                        reference.child(uid).child("Pet2").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(admitPetAdmin.this, petName2 +" has been admitted", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void admitThisPet3(String uid) {
        reference2 = FirebaseDatabase.getInstance().getReference("/Pets/" + uid+"/Pet");
        reference2.child("Pet3").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("petName").getValue());
                        String species = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String breed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String age = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String birthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String imgUrl = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        petName3 = name;
                        petBreed3 = breed;
                        petDp3 = imgUrl;

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm a");
                        String currentDate = formatter.format(new Date());
                        String currentTime = formatterTime.format(new Date());


                        HashMap<Object, String> hashMap = new HashMap<>();

                        hashMap.put("petName", petName3);
                        hashMap.put("breed", petBreed3);
                        hashMap.put("petPic", petDp3);
                        hashMap.put("date", currentDate);
                        hashMap.put("time", currentTime);
                        hashMap.put("status", "Admitted");


                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        DatabaseReference reference = database.getReference("Monitoring");
                        reference.child(uid).child("Pet3").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(admitPetAdmin.this, petName3 +" has been admitted", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}