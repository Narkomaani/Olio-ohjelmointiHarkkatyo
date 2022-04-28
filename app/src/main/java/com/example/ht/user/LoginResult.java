package com.example.ht.user;

import android.util.Patterns;

/**
 * a class to check if username and password are valid
 * code take from Android studio's Login activity example
 */
public class LoginResult {


    // A username validation check
    public static boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        // if using an email, check the pattern
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A password validation check
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public static boolean validate(String username, String password) {
        String usr = UserManager.getUserManager().getCurrentUser().getUsername();
        String pwd = UserManager.getUserManager().getCurrentUser().getPassword();
        return usr.equals(username) && pwd.equals(password);
    }
}
