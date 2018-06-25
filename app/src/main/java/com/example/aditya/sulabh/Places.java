package com.example.aditya.sulabh;

/**
 * Created by aditya on 25/6/18.
 */

public class Places {

    private String vicinity;
    private int image;

    public Places(String vicinity,int filetype){
        this.vicinity = vicinity;
        this.image = filetype;

    }

    public String getVicinity() {
        return vicinity;
    }

    public int getImage() {
        return image;
    }
}
