package com.danasoftprototype.govet;

public class petAdmitMonitoring {
    String petName, date, time, status, breed, petPic, name, uid;

    public petAdmitMonitoring(){}

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getPetPic() {
        return petPic;
    }

    public void setPetPic(String petPic) {
        this.petPic = petPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public petAdmitMonitoring(String petName, String date, String time, String status, String breed, String petPic, String name, String uid) {
        this.petName = petName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.breed = breed;
        this.petPic = petPic;
        this.name = name;
        this.uid = uid;
    }
}
