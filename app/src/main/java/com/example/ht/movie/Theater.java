package com.example.ht.movie;

import androidx.appcompat.app.AppCompatActivity;

public class Theater extends AppCompatActivity {
    private final int id;
    private final String name;

    public Theater(int id, String name){

        this.id = id;
        this.name = name;
    }

    public int GetId(){
        return id;
    }

    public String GetName(){
        return name;
    }
}
