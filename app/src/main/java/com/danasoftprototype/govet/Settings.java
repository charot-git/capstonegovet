package com.danasoftprototype.govet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.settings.aboutus;
import com.danasoftprototype.govet.settings.account;
import com.danasoftprototype.govet.settings.display;
import com.danasoftprototype.govet.settings.help;
import com.danasoftprototype.govet.settings.privacyterm;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Settings() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView account = (TextView) view.findViewById(R.id.accountview);
        TextView display = (TextView) view.findViewById(R.id.displayview);
        TextView privacyterms = (TextView) view.findViewById(R.id.privacyandtermsview);
        TextView aboutus = (TextView) view.findViewById(R.id.aboutusview);
        TextView help = (TextView) view.findViewById(R.id.helpview);
        TextView logout = (TextView) view.findViewById(R.id.logoutview);
        TextView logs = (TextView) view.findViewById(R.id.logsview);

        account.setOnClickListener(this::accountmethod);
        display.setOnClickListener(this::displaymethod);
        privacyterms.setOnClickListener(this::privacytermsmethod);
        aboutus.setOnClickListener(this::aboutusmethod);
        help.setOnClickListener(this::helpmethod);
        logout.setOnClickListener(this::logoutmethod);
        logs.setOnClickListener(this::logsmethod);
    }


    private void logoutmethod(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity().getApplication(), "Logout successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void helpmethod(View view){
        Intent intent = new Intent(getActivity().getApplication(), help.class);
        startActivity(intent);
    }

    private void aboutusmethod(View view) {
        Intent intent = new Intent(getActivity().getApplication(), aboutus.class);
        startActivity(intent);
    }

    private void privacytermsmethod(View view) {
        Intent intent = new Intent(getActivity().getApplication(), privacyterm.class);
        startActivity(intent);
    }

    private void displaymethod(View view) {
        Intent intent = new Intent(getActivity().getApplication(), display.class);
        startActivity(intent);
    }

    private void logsmethod(View view) {
        Intent intent = new Intent(getActivity().getApplication(), UserLogs.class);
        startActivity(intent);

    }

    private void accountmethod(View view) {
        Intent intent = new Intent(getActivity().getApplication(), account.class);
        startActivity(intent);
    }
}