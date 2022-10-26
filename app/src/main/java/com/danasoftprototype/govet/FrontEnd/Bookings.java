package com.danasoftprototype.govet.FrontEnd;

public class Bookings {
    private String time;
    private String day;
    private String month;
    private String year;
    private String date;
    private String description;
    private String name;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bookings(String time, String day, String month, String year, String date, String description, String name) {
        this.time = time;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = date;
        this.description = description;
        this.name = name;
    }
//from date to day/week/month/year

    public Bookings(){}


}
