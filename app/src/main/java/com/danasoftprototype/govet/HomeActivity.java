package com.danasoftprototype.govet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public static class admittanceMonitoring {
        private String status;
        private String petName;
        private String petBreed;
        private String dateLastUpdate;
        private String timeLastUpdate;

        public void admittanceMonitoring(){}

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPetName() {
            return petName;
        }

        public void setPetName(String petName) {
            this.petName = petName;
        }

        public String getPetBreed() {
            return petBreed;
        }

        public void setPetBreed(String petBreed) {
            this.petBreed = petBreed;
        }

        public String getDateLastUpdate() {
            return dateLastUpdate;
        }

        public void setDateLastUpdate(String dateLastUpdate) {
            this.dateLastUpdate = dateLastUpdate;
        }

        public String getTimeLastUpdate() {
            return timeLastUpdate;
        }

        public void setTimeLastUpdate(String timeLastUpdate) {
            this.timeLastUpdate = timeLastUpdate;
        }

        public admittanceMonitoring(String status, String petName, String petBreed, String dateLastUpdate, String timeLastUpdate) {
            this.status = status;
            this.petName = petName;
            this.petBreed = petBreed;
            this.dateLastUpdate = dateLastUpdate;
            this.timeLastUpdate = timeLastUpdate;
        }
    }
}