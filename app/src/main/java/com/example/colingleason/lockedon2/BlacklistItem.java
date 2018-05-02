package com.example.colingleason.lockedon2;

/**
 * Created by Colin Gleason on 11/1/2017.
 */

public class BlacklistItem {

    String name;
    boolean allowed;

    BlacklistItem(String name){
        this.name = name;
        this.allowed = false;
    }

    public void setAllowed(){
        allowed = !allowed;
    }
}
