package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ht.user.LoginResult;
import com.example.ht.user.UserManager;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private CheckBox rememberMe;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.gotoRegisterButton);
        rememberMe = findViewById(R.id.rememberMe);

        sharedPreferences = getApplicationContext().getSharedPreferences("userDB", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userManager = UserManager.getUserManager();


        // if checkbox was checked, import credentials
        if (sharedPreferences != null) {
                if (sharedPreferences.getBoolean("remember_me_checkbox", false)) {
                    username.setText(sharedPreferences.getString("last_used_username", ""));
                    password.setText(sharedPreferences.getString("last_used_password", ""));
                    rememberMe.setChecked(true);
                }
        }

        // setting up remember listener
        rememberMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("remember_me_checkbox", rememberMe.isChecked())
                        .apply();
            }
        });

        // setting up register button
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        // setting up login button
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            String inputUsername = username.getText().toString();
            String inputPassword = password.getText().toString();

            // checking if inputs were empty
            if (inputPassword.isEmpty() || inputUsername.isEmpty()) {
                Snackbar.make(view, "Please enter all details.", Snackbar.LENGTH_LONG).show();
            } else {
                // validate login credentials
                if (LoginResult.validate(inputUsername, inputPassword)) {

                    editor.putString("last_used_username", inputUsername);
                    editor.putString("last_used_password", inputPassword);


                    Snackbar.make(view, "Login successful.", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Snackbar.make(view, "Invalid login credentials.", Snackbar.LENGTH_LONG).show();
                }
            }


            }
        });
    }
}