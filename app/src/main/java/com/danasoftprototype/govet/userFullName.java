package com.danasoftprototype.govet;

public class userFullName {
    private String name;
    private String fname;
    private String mname;
    private String lname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public userFullName(String name, String fname, String mname, String lname) {
        this.name = name;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
    }
}
