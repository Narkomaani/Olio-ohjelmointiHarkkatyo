package com.example.ht.user;

/**
 * base class for users, extending userprofile to get its data
 */
public class User extends UserProfile {
    private String username = "mattimeikalainen";
    private String password = "salasana123";


    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    // empty constructor for debugging
    public User(){

    }


    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

}
