package com.example.ht.User;

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
        char chr;
        boolean upperFlag = false;
        boolean lowerFlag = false;
        // checks every character to find if password contains upper and lower case
        for (int i=0; i < password.length(); i++) {
            chr = password.charAt(i);
            if (Character.isUpperCase(chr)) {
                upperFlag = true;
            } else if (Character.isLowerCase(chr)) {
                lowerFlag = true;
            }
        }

        return password != null && password.trim().length() > 12 && upperFlag && lowerFlag;
    }

    public static boolean validate(String username, String password) {
        String usr = UserManager.getUserManager().getCurrentUser().getUsername();
        String pwd = UserManager.getUserManager().getCurrentUser().getPassword();
        return usr.equals(username) && pwd.equals(password);
    }
}
