package com.danasoftprototype.govet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassDialog extends AppCompatDialogFragment {

    private EditText newpassword;
    private EditText newconfpassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.changepass, null);

        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Change password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newpass;
                String newconfpass;

                newpass = newpassword.getText().toString();
                newconfpass = newconfpassword.getText().toString();

                if (newpass.isEmpty()){
                    newpassword.setError("Please enter a new password!");
                    newpassword.requestFocus();
                }
                else if(newconfpass.isEmpty()){
                    newconfpassword.setError("This field is required!");
                    newpassword.requestFocus();
                }
                else if (!newconfpass.equals(newpass)){
                    Toast.makeText(getActivity().getApplication(), "The passwords do not match", Toast.LENGTH_SHORT).show();
                    return;

                }
                else{

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(newpass);
                }

            }
        });

        newpassword = view.findViewById(R.id.newpass);
        newconfpassword = view.findViewById(R.id.confnewpass);


        return builder.create();
    }

}
