package com.danasoftprototype.govet;

import android.widget.EditText;

public class User {


    private String username;
    private String email;
    private String profilepic;
    private String mobilenumber;

    public User(){

    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public User(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }


    public User(String username, String email, String profilepic, String mobilenumber) {
        this.username = username;
        this.email = email;
        this.profilepic = profilepic;
        this.mobilenumber = mobilenumber;
    }



}
