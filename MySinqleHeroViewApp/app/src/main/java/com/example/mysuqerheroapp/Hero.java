package com.example.mysuqerheroapp;

public class Hero {
    private String name;
    private String universe;
    private String gender;
    private int image;
    private String url;

    public Hero(String name, String universe, String gender, int image, String url) {
        this.name = name;
        this.universe = universe;
        this.gender = gender;
        this.image = image;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUniverse() {
        return universe;
    }

    public String getGender() {
        return gender;
    }

    public int getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
