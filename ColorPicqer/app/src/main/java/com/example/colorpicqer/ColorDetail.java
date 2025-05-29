package com.example.colorpicqer;

public class ColorDetail {
    private String name;
    private String hex;
    private String wrongHex;

    public ColorDetail(String name, String hex, String wrongHex) {
        this.name = name;
        this.hex = hex;
        this.wrongHex = wrongHex;
    }

    public String getName() {
        return name;
    }

    public String getHex() {
        return hex;
    }

    public String getWrongHex() {
        return wrongHex;
    }
}
