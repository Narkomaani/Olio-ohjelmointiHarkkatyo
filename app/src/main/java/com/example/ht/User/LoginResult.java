package com.example.ht.User;

import android.content.Context;
import android.util.Patterns;

/**
 * a class to check if username and password are valid
 * code take from Android studio's Login activity example
 */
public abstract class LoginResult {

    private static UserManager userManager = UserManager.getUserManager();


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

    public static User validate(String username, String password, Context context) {
        UserDatabase userDB = UserDatabase.getInstance(context);
        User user = userDB.userDao().findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                userManager.setCurrentUser(user);

                return user;
            }
        }
        return null;
    }
}
