package com.example.ht.user;

import java.util.ArrayList;

/***
 * The Class tha manages users and its data
 */
public class UserManager{

    private static UserManager userManager = new UserManager();
    private ArrayList<User> Users;
    private User currentUser;


    private UserManager() {
        currentUser = new User();
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
