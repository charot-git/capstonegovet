package com.danasoftprototype.govet;

public class Logs {
    String name, date, time, status;

    public Logs(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Logs(String name, String date, String time, String status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }
}
