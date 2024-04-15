package com.example.m;

public class Medicine {
    private String name;
    private String type;
    private String medImage;
    private int dose;

    public Medicine(String name) {
        this.name = name;
        //this.type = type;
        //this.medImage = medImage;
        //this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedImage() {
        return medImage;
    }

    public void setMedImage(String medImage) {
        this.medImage = medImage;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }
}
