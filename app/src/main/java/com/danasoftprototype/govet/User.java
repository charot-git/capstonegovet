package com.danasoftprototype.govet;

import android.widget.EditText;

public class User {

    private String username;

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

    private String email;
    private String profilepic;

    public User(){

    }

    public User(String username, String email, String profilepic) {
        this.username = username;
        this.email = email;
        this.profilepic = profilepic;
    }



}
