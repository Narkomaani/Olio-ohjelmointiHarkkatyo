package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends MainActivity {

    Spinner spinnerLanguages;
    EditText newEmailAddress;
    TextView oldEmailAddress;
    Button setEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinnerLanguages = findViewById(R.id.spinner_languages);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        oldEmailAddress = findViewById(R.id.oldEmailAddressText);
        newEmailAddress = findViewById(R.id.newEmailAddressText);
        setEmailAddress = findViewById(R.id.setEmailAddress);

        setEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldEmailAddress.setText(newEmailAddress.getText().toString());
            }
        });

    }
}