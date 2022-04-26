package com.example.ht.user;

public class UserManager{

    private UserManager userManager;
    private User[] Users;
    private User currentUser;


    private UserManager() {
    }

    public UserManager getUserManager() {
        return userManager;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getUser(int userid) {
        for (User user : Users) {
            if (user.getUserid() == userid) {
                return user;
            }
        }
        return null;
    }
}
