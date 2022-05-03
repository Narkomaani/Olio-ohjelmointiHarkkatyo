package com.example.ht.user;

/***
 * The Class tha manages users and its data
 */
public class UserManager{

    private static final UserManager userManager = new UserManager();
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
