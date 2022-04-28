package com.example.ht.user;

/**
 * class holding all displayable data of user
 */
public class UserProfile {
    private String FirstName = "Matti";
    private String LastName = "Meikäläinen";
    private String email = "matti.meikalainen@gmail.com";
    private String location = "Mattila";

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
