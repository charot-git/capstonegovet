package com.danasoftprototype.govet.FrontEnd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.Monitor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link monitor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class monitor extends Fragment {

    ImageView profilepic;
    TextView name, breed, date, time, status;
    List <Monitor> monitorList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public monitor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment monitor.
     */
    // TODO: Rename and change types and number of parameters
    public static monitor newInstance(String param1, String param2) {
        monitor fragment = new monitor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        monitorList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilepic = view.findViewById(R.id.profilepic);
        name = view.findViewById(R.id.monitorname);
        status = view.findViewById(R.id.petStatus);
        breed = view.findViewById(R.id.monitorbreed);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Monitoring");
        reference.child(FirebaseAuth.getInstance().getUid()).child("Pet1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String dp = String.valueOf(dataSnapshot.child("petPic").getValue());
                        String petName = String.valueOf(dataSnapshot.child("petName").getValue());
                        String petStatus = String.valueOf(dataSnapshot.child("status").getValue());
                        String petBreed = String.valueOf(dataSnapshot.child("breed").getValue());
                        String petDate = String.valueOf(dataSnapshot.child("date").getValue());
                        String petTime = String.valueOf(dataSnapshot.child("time").getValue());

                        Picasso.get().load(dp).placeholder(R.drawable.logogv).into(profilepic);
                        name.setText(petName);
                        status.setText(petStatus);
                        breed.setText(petBreed);
                        date.setText(petDate);
                        time.setText(petTime);

                    }
                }

            }
        });


    }
}