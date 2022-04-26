package com.example.ht.user;

/**
 * base class for users, extending userprofile to get its data
 */
public class User extends UserProfile {
    private String username = "mattimeikalainen";
    private int userid = 12345;
    private String password = "";


    public User(String username, String password, int userid) {
        setUsername(username);
        setPassword(password);
        setUserid(userid);
    }

    public User(){

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
