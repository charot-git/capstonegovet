package com.danasoftprototype.govet.FrontEnd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.Pet;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.addPet2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class profile extends Fragment {

    DatabaseReference reference;

    ImageView profilepic , back ,petpic1, petpic2, petpic3;
    FirebaseAuth mAuth;
    TextView addpet ,addpet2, addpet3;
    LinearLayout pet1Layout, pet2Layout ,pet3Layout;
    TextView petname1, petage1, petbreed1;
    TextView petname2, petage2, petbreed2;
    TextView petname3, petage3, petbreed3;
    StorageReference storageReference;

    private ArrayList<Pet> pet;
    
    CardView pet1, pet2view, pet3view, petpic2container, petpic3container;

    public profile() {
        // Required empty public constructor
    }

    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //1st event
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("images/" + mAuth.getCurrentUser().getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(250,250).centerCrop().into(profilepic);
            }
        });


        getData();

    }

    private void getData() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //2nd event
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //3rd event
        super.onViewCreated(view, savedInstanceState);

        {
            profilepic = view.findViewById(R.id.profilepic);
            back = view.findViewById(R.id.back);

            pet1Layout = view.findViewById(R.id.pet1Layout);
            petname1 = view.findViewById(R.id.petname1);
            petage1 = view.findViewById(R.id.year1);
            petbreed1 = view.findViewById(R.id.breed1);
            petpic1 = view.findViewById(R.id.petPic1);
            addpet = view.findViewById(R.id.addPet1);

            pet2Layout = view.findViewById(R.id.pet2Layout);
            petname2 = view.findViewById(R.id.petname2);
            petage2 = view.findViewById(R.id.year2);
            petbreed2 = view.findViewById(R.id.breed2);
            petpic2 = view.findViewById(R.id.petPic2);
            pet2view = view.findViewById(R.id.pet2);
            petpic2container = view.findViewById(R.id.petPic2Container);
            addpet2 = view.findViewById(R.id.addPet2);

            pet3Layout = view.findViewById(R.id.pet3Layout);
            petname3 = view.findViewById(R.id.petname3);
            petage3 = view.findViewById(R.id.year3);
            petbreed3 = view.findViewById(R.id.breed3);
            petpic3 = view.findViewById(R.id.petPic3);
            pet3view = view.findViewById(R.id.pet3);
            petpic3container = view.findViewById(R.id.petPic3Container);
            addpet3 = view.findViewById(R.id.addPet3);
        }

        final FirebaseUser user = mAuth.getInstance().getCurrentUser();
        Button btn = (Button) view.findViewById(R.id.updateprofile);
        TextView notverified = (TextView) view.findViewById(R.id.notverifiedtext);
        if (!user.isEmailVerified()) {
            notverified.setVisibility(View.VISIBLE);
        }



        //listeners for add pet
        addpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.danasoftprototype.govet.addPet2.class);
                startActivity(intent);

            }
        });

        addpet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.danasoftprototype.govet.addPet2.class);
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplication(), profileupdate.class);
                startActivity(intent);
            }
        });


        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), govethome.class);
                startActivity(intent);
            }
        });


        //execute if no pet yet
        {
            /*if (FirebaseDatabase.getInstance().getReference("user/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pet1").toString().equals("null")) {*/

                String pet1 = "pet1";
                reference = FirebaseDatabase.getInstance().getReference("user/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.child(pet1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String petName = String.valueOf(dataSnapshot.child("petName").getValue());
                            String petAge = String.valueOf(dataSnapshot.child("petAge").getValue());
                            String petBreed = String.valueOf(dataSnapshot.child("petBreed").getValue());
                            String petSpecies = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                            String petBirthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                            String petProfilePic = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                            addpet.setVisibility(View.INVISIBLE);
                            pet1Layout.setVisibility(View.VISIBLE);
                            petname1.setText(petName);
                            petage1.setText(petAge);
                            petbreed1.setText(petBreed);
                            Picasso.get().load(petProfilePic).into(petpic1);


                            pet2view.setVisibility(View.VISIBLE);
                            petpic2container.setVisibility(View.INVISIBLE);
                            addpet2.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), "Failed to read", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            //}
/*
            else if (FirebaseDatabase.getInstance().getReference("user/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pet2").toString().equals("null")) {
*/
            String pet2 = "pet2";
                reference = FirebaseDatabase.getInstance().getReference("user/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.child(pet2).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String petName2 = String.valueOf(dataSnapshot.child("petName").getValue());
                            String petAge2 = String.valueOf(dataSnapshot.child("petAge").getValue());
                            String petBreed2 = String.valueOf(dataSnapshot.child("petBreed").getValue());
                            String petSpecies = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                            String petBirthday = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                            String petProfilePic2 = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                            addpet2.setVisibility(View.INVISIBLE);
                            pet2Layout.setVisibility(View.VISIBLE);
                            petname2.setText(petName2);
                            petage2.setText(petAge2);
                            petbreed2.setText(petBreed2);
                            Picasso.get().load(petProfilePic2).into(petpic2);


                        } else {
                            Toast.makeText(getActivity(), "Failed to read", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            String pet3 = "pet3";
            reference = FirebaseDatabase.getInstance().getReference("user/pet/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            reference.child(pet3).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String petName3 = String.valueOf(dataSnapshot.child("petName").getValue());
                        String petAge3 = String.valueOf(dataSnapshot.child("petAge").getValue());
                        String petBreed3 = String.valueOf(dataSnapshot.child("petBreed").getValue());
                        String petSpecies3 = String.valueOf(dataSnapshot.child("petSpecies").getValue());
                        String petBirthday3 = String.valueOf(dataSnapshot.child("petBirthday").getValue());
                        String petProfilePic3 = String.valueOf(dataSnapshot.child("petProfilePic").getValue());

                        pet3Layout.setVisibility(View.VISIBLE);
                        petname3.setText(petName3);
                        petage3.setText(petAge3);
                        petbreed3.setText(petBreed3);
                        Picasso.get().load(petProfilePic3).into(petpic3);


                        pet3view.setVisibility(View.VISIBLE);
                        petpic3container.setVisibility(View.VISIBLE);
                        addpet3.setVisibility(View.INVISIBLE);


                    } else {
                        Toast.makeText(getActivity(), "Failed to read", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
        }
   // }




}