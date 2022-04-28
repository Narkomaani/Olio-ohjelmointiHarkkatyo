package com.example.ht.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * The Class tha manages users and its data
 */
public class UserManager{

    private static UserManager userManager = new UserManager();
    private HashMap<String, User> userHashMap = new HashMap<>();
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

    public void addUser(User user) {
        userHashMap.put(user.getUsername(), user);
    }

    public boolean checkUsername(String username) {
        return userHashMap.containsKey(username);
    }

    public boolean checkData(String username, User user) {
        // check if username exists
        if (getUserManager().checkUsername(username)) {
            // checks if password matches
            return user.getPassword().equals(userHashMap.get(username).getPassword());
        }
        return false;
    }

    public void saveData(Map<String, ?> preferenceMap) {

        /* TODO get communication with hashmap and userDB fixed
        for (Map.Entry<String, ?> entry : preferenceMap.entrySet()) {
            userHashMap.put(entry.getKey(), entry.getValue().toString());
        }

         */
    }
}
