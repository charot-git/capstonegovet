package com.danasoftprototype.govet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.danasoftprototype.govet.startUp.loginpage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassDialog extends AppCompatDialogFragment {

    String TAG;
    private EditText newpassword;
    private EditText oldpassword;
    private MaterialButton button;
    FirebaseUser fuser;
    AuthCredential credential;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.changepass, null);



        fuser = FirebaseAuth.getInstance().getCurrentUser();
        newpassword = view.findViewById(R.id.newpass);
        oldpassword = view.findViewById(R.id.oldpass);



        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Password change cancelled", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();





        button = (MaterialButton) view.findViewById(R.id.confirm);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String email = fuser.getEmail();
                String newpass = newpassword.getText().toString();
                String oldpass = oldpassword.getText().toString();

                credential = EmailAuthProvider.getCredential(email, oldpass);


                fuser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            if(newpass.isEmpty()){
                                newpassword.setError("Please enter your new password");
                                newpassword.requestFocus();
                            }
                            else if(oldpass.isEmpty()){
                                oldpassword.setError("Please enter your old password");
                                oldpassword.requestFocus();
                            }
                            else if(newpass.equals(oldpass)){
                                oldpassword.setError("Please enter a new password");
                                newpassword.requestFocus();
                            }
                            else {
                                fuser.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Password change successful", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            Intent intent = new Intent(getActivity(), loginpage.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getActivity(), "Error in changing the password", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }

                        }
                        else{
                            Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });
        return builder.create();
    }



}