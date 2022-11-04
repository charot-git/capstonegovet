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

import com.bumptech.glide.Glide;
import com.danasoftprototype.govet.Pet;
import com.danasoftprototype.govet.R;
import com.danasoftprototype.govet.addPet;
import com.danasoftprototype.govet.addPet2;
import com.danasoftprototype.govet.addPet3;
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

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    ImageView profilepic, back, petpic1, petpic2, petpic3;
    FirebaseAuth mAuth;
    TextView addpet, addpet2, addpet3;
    LinearLayout pet1Layout, pet2Layout, pet3Layout;
    TextView petname1, petage1, petbreed1;
    TextView petname2, petage2, petbreed2;
    TextView petname3, petage3, petbreed3;
    StorageReference storageReference;
    DatabaseReference reference1;
    DatabaseReference reference2;
    DatabaseReference reference3;

    private ArrayList<Pet> pet;

    CardView petpic1container, pet1view,  pet2view, pet3view, petpic2container, petpic3container;

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
                Picasso.get().load(uri).resize(250, 250).centerCrop().into(profilepic);
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
            profilepic = view.findViewById(R.id.profilepic);
            back = view.findViewById(R.id.back);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

            petpic1container = view.findViewById(R.id.petPic1Container);
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
                Intent intent;
                intent = new Intent(getActivity(), addPet.class);
                startActivity(intent);

            }
        });
        addpet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), addPet2.class);
                startActivity(intent);

            }
        });

        addpet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), addPet3.class);
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



        tryRead();

    }

    private void tryRead() {
        String pet1 = "pet1";

        reference1 =FirebaseDatabase.getInstance().getReference("/Pets/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Pet");
        reference1.child("Pet1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        pet1Show();
                        pet2view.setVisibility(View.VISIBLE);

                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }
                    
                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String pet2 = "pet2";

        reference1 =FirebaseDatabase.getInstance().getReference("/Pets/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Pet");
        reference1.child("Pet2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        pet2Show();
                        pet3view.setVisibility(View.VISIBLE);

                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String pet3 = "pet3";

        reference3 =FirebaseDatabase.getInstance().getReference("/Pets/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Pet");
        reference3.child("Pet3").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        pet3Show();

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

    private void pet1Show() {
        reference1 =FirebaseDatabase.getInstance().getReference("/Pets/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Pet");
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


                        addpet.setVisibility(View.INVISIBLE);
                        petpic1container.setVisibility(View.VISIBLE);
                        pet1Layout.setVisibility(View.VISIBLE);
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
    }

    private void pet2Show(){
        reference2 =FirebaseDatabase.getInstance().getReference("/Pets/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Pet");
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


                        addpet2.setVisibility(View.INVISIBLE);
                        petpic2container.setVisibility(View.VISIBLE);
                        pet2Layout.setVisibility(View.VISIBLE);
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
    private void pet3Show(){
        reference2 =FirebaseDatabase.getInstance().getReference("/Pets/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Pet");
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


                        addpet3.setVisibility(View.INVISIBLE);
                        petpic3container.setVisibility(View.VISIBLE);
                        pet3Layout.setVisibility(View.VISIBLE);
                        petname3.setText(name);
                        petage3.setText(age);
                        petbreed3.setText(breed);
                        Picasso.get().load(imgUrl).into(petpic3);

                    }else{
                        //Toast.makeText(getActivity(), "data does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                   // Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
