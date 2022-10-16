package com.danasoftprototype.govet;

public class Pet {


    private String petSpecies;
    private String petName;
    private String petAge;
    private String petBreed;
    private String petBirthday;
    private String petProfilePic;

    public Pet(){

    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(String petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetProfilePic() {
        return petProfilePic;
    }

    public void setPetProfilePic(String petProfilePic) {
        this.petProfilePic = petProfilePic;
    }


    public Pet(String petSpecies, String petName, String petAge, String petBreed, String petBirthday, String petProfilePic) {
        this.petSpecies = petSpecies;
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petBirthday = petBirthday;
        this.petProfilePic = petProfilePic;
    }

}

