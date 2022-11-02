package com.danasoftprototype.govet.FrontEndAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEnd.booking;
import com.danasoftprototype.govet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class adminUsersDelete extends AppCompatActivity {
    TextView name, email;
    Button delete, admit;
    ImageView userPicUser;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_delete);

        userPicUser = findViewById(R.id.userPicMon);
        name = findViewById(R.id.userText);
        email = findViewById(R.id.emailText);
        delete = findViewById(R.id.button4);
        admit = findViewById(R.id.admitPet);

        String nameUser = getIntent().getStringExtra("name");
        String emailUser = getIntent().getStringExtra("email");
        String userPic = getIntent().getStringExtra("image");
        String uid = getIntent().getStringExtra("uid");

        builder = new AlertDialog.Builder(this);

        name.setText(nameUser);
        email.setText(emailUser);

        Picasso.get().load(userPic).error(R.drawable.logogv).placeholder(R.drawable.logogv).into(userPicUser);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMethod(uid);
            }
        });

        admit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), admitPetAdmin.class).putExtra("name", nameUser).putExtra("uid", uid));
                finish();
            }
        });

    }

    private void deleteMethod(String uid) {

        builder.setMessage("Delete this user?" ).setTitle("User deletion")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(adminUsersDelete.this, "Database of user has been deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplication(), adminUsers.class));
                                }
                            }
                        });

                        DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("Pets");
                        petRef.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(adminUsersDelete.this, "Database of pets for user has been deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(), "Deletion cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Delete user");
        alertDialog.show();


    }
}