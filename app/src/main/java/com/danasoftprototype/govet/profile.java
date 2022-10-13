package com.danasoftprototype.govet;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class profile extends Fragment {

    ImageView profilepic;
    StorageReference storageReference;
    StorageReference profileRef;
    FirebaseAuth mAuth;
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



        final FirebaseUser user = mAuth.getInstance().getCurrentUser();
        Button btn = (Button) view.findViewById(R.id.updateprofile);
        TextView notverified = (TextView) view.findViewById(R.id.notverifiedtext);
        if(!user.isEmailVerified()){
            notverified.setVisibility(View.VISIBLE);
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplication(), profileupdate.class);
                startActivity(intent);
            }
        });

        /*
        profilepic = (ImageView) view.findViewById(R.id.profilepic);

        storageReference = FirebaseStorage.getInstance().getReference();
        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });
        */
    }



}