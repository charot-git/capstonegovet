package com.danasoftprototype.govet;

public class ModelUser {
    String email, image, name, phone, uid, username;


    public  ModelUser(){}


    public ModelUser(String email, String image, String name, String phone, String uid, String username) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.uid = uid;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
