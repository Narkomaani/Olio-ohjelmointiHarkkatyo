package com.example.ht;

/**
 * class holding all displayable data of user
 */
public class UserProfile {
    private String FirstName = "Matti";
    private String LastName = "Meikäläinen";
    private String email = "matti.meikalainen@gmail.com";
    private String location = "Mattila";

    public  void  setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
