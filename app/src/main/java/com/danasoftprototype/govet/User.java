package com.danasoftprototype.govet;

import android.widget.EditText;

public class User {


    private String Username;
    private String Email;
    private String Profilepic;
    private String Mobilenumber;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfilepic() {
        return Profilepic;
    }

    public void setProfilepic(String profilepic) {
        Profilepic = profilepic;
    }

    public String getMobilenumber() {
        return Mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        Mobilenumber = mobilenumber;
    }

    public User(String username, String email, String profilepic, String mobilenumber) {
        Username = username;
        Email = email;
        Profilepic = profilepic;
        Mobilenumber = mobilenumber;
    }
}
