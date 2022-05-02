package com.example.ht.User;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

/**
 * class holding all displayable data of user
 */
public class UserProfile {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    private String FirstName = "Matti";

    @ColumnInfo(name = "last_name")
    private String LastName = "Meikäläinen";

    @ColumnInfo(name = "email")
    private String email = "matti.meikalainen@gmail.com";

    public UserProfile setFirstName(String FirstName) {
        this.FirstName = FirstName;
        return this;
    }

    public String getFirstName() {
        return FirstName;
    }

    public UserProfile setLastName(String lastName) {
        this.LastName = lastName;
        return this;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

    public UserProfile setEmail(String email) {
        this.email = email;
        return this;
    }
}

