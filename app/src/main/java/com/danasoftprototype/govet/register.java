package com.danasoftprototype.govet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class register extends AppCompatActivity {

    EditText usernamereg, passwordreg, repass;
    MaterialButton register;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernamereg = (EditText) findViewById(R.id.usernamereg);
        passwordreg = (EditText) findViewById(R.id.passwordreg);
        repass = (EditText) findViewById(R.id.repass);
        register = (MaterialButton) findViewById(R.id.register);
        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userreg = usernamereg.getText().toString();
                String passreg = passwordreg.getText().toString();
                String confirmpassreg = repass.getText().toString();


                if (userreg.equals("")||passreg.equals("")||confirmpassreg.equals("")){
                    Toast.makeText(register.this,"Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(passreg.equals(confirmpassreg)){
                        Boolean checkuser = DB.checkusername(userreg);
                            if (checkuser==false){
                                Boolean insert = DB.insertData(userreg,passreg);
                                if(insert==true){
                                    Toast.makeText(register.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }

                            }
                            else{
                                Toast.makeText(register.this,"The user " + userreg + " already exists", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }

                    }
                }
            }
        });


    }
}