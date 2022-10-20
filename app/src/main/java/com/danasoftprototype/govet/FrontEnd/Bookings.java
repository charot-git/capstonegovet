package com.danasoftprototype.govet.FrontEnd;

public class Bookings {
    private String time;
    private String day;
    private String month;
    private String year;
    private String date;
//from date to day/week/month/year


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bookings(String time, String day, String month, String year, String date) {
        this.time = time;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = date;
    }
}
