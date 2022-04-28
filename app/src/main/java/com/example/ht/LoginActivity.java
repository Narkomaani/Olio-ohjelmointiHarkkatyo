package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.ht.user.LoginResult;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.gotoRegisterButton);

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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Snackbar.make(view, "invalid login credentials.", Snackbar.LENGTH_LONG).show();
                }
            }


            }
        });
    }
}