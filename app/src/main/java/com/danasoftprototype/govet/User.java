package com.danasoftprototype.govet;

import android.widget.EditText;

public class User {

    public String username, number, email;

    public User(EditText username, String email, String number){

    }

    public User(String username, String number, String email){
        this.email = email;
        this.number = number;
        this.email = email;
    }
}
