package com.danasoftprototype.govet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.danasoftprototype.govet.FrontEndAdmin.govethome2;
import com.danasoftprototype.govet.FrontEndVet.govethome3;
import com.danasoftprototype.govet.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    DatabaseReference reference2;
    TextView announcement;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        announcement = binding.annHome;
        ifUserIsAdmin();
        ifUserIsVet();
        ifAdminHasPost();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void ifAdminHasPost() {
        reference2 = FirebaseDatabase.getInstance().getReference("Posts");
        reference2.child("adminPosts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        DataSnapshot dataSnapshot = task.getResult();

                        String postFromDatabase = String.valueOf(dataSnapshot.child("post").getValue());
                        announcement.setText(postFromDatabase);
                    }

                }

            }
        });
    }

    private void ifUserIsAdmin() {

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admingovet@gmail.com")) {
            startActivity(new Intent(getActivity(), govethome2.class));
        }
    }

    private void ifUserIsVet() {
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("vetgovet@gmail.com")) {
            startActivity(new Intent(getActivity(), govethome3.class));

        }

    }



}