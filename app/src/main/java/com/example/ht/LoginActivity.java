package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ht.User.LocaleHelper;
import com.example.ht.User.LoginResult;
import com.example.ht.User.User;
import com.example.ht.User.UserDao;
import com.example.ht.User.UserDatabase;
import com.example.ht.User.UserManager;
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

        // variable set-up
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.gotoRegisterButton);
        rememberMe = findViewById(R.id.rememberMe);

        userManager = UserManager.getUserManager();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();


        // if checkbox was checked, import credentials
        if (sharedPreferences != null) {
                if (sharedPreferences.getBoolean("remember_me_checkbox", false)) {
                    username.setText(userManager.getCurrentUser().getUsername());
                    password.setText(userManager.getCurrentUser().getPassword());
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
                Snackbar.make(view, getString(R.string.login_empty), Snackbar.LENGTH_LONG).show();
            } else {
                // validate login credentials
                User user = LoginResult.validate(inputUsername, inputPassword, getApplicationContext());
                if (user != null) {
                    // since the currentUser has been set in validate(), we just goto the main activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    Snackbar.make(view, getString(R.string.login_invalid), Snackbar.LENGTH_LONG).show();
                }
            }


            }
        });
    }
}