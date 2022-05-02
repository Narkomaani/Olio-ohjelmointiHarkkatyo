package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ht.User.LoginResult;
import com.example.ht.User.User;
import com.example.ht.User.UserManager;
import com.google.android.material.snackbar.Snackbar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private Button registerUserButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        registerUserButton = findViewById(R.id.registerButton);
        firstname = findViewById(R.id.firstnameRegister);
        lastname = findViewById(R.id.lastnameRegister);
        email = findViewById(R.id.emailRegister);


        sharedPreferences = getApplicationContext().getSharedPreferences("userDB", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iUsername = username.getText().toString();
                String iPassword = password.getText().toString();

                UserManager userManager = UserManager.getUserManager();

                // check if any data is empty
                if (username.getText().toString().isEmpty()) {
                    Snackbar.make(view, "please enter an username", Snackbar.LENGTH_LONG).show();
                } else if (password.getText().toString().isEmpty()) {
                    Snackbar.make(view, "please enter a password", Snackbar.LENGTH_LONG).show();
                } else if (firstname.getText().toString().isEmpty()) {
                    Snackbar.make(view, "please enter a first name", Snackbar.LENGTH_LONG).show();
                } else if (lastname.getText().toString().isEmpty()) {
                    Snackbar.make(view, "please enter a last name", Snackbar.LENGTH_LONG).show();
                } else if (email.getText().toString().isEmpty()) {
                    Snackbar.make(view, "please enter a email", Snackbar.LENGTH_LONG).show();
                } else

                // validate the format of username and password
                 if (LoginResult.isUserNameValid(iUsername) && LoginResult.isPasswordValid(iPassword)){

                     if(userManager.checkUsername(iUsername)) {
                         Snackbar.make(view, "Username already taken", Snackbar.LENGTH_LONG).show();
                     } else {
                         // Making the new user
                         User newUser = (User) new User(iUsername, iPassword)
                                 .setFirstName(firstname.getText().toString())
                                 .setLastName(lastname.getText().toString())
                                 .setEmail(email.getText().toString());

                         userManager.setCurrentUser(newUser);
                         userManager.addUser(newUser);

                         // adding all data into the database
                         editor.putString(iUsername, iPassword);
                         editor.apply();

                         Snackbar.make(view, "Registration successful", Snackbar.LENGTH_LONG).show();
                         startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                     }
                 } else {
                     Snackbar.make(view, "please enter a valid username and password", Snackbar.LENGTH_LONG).show();
                 }

            }
        });
    }
}