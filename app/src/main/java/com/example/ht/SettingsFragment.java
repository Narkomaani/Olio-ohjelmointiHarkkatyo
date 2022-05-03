package com.example.ht;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    UserManager userManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        EditTextPreference emailAddressPreference = findPreference("emailAddress");
        emailAddressPreference.setSummary("testi");

        EditTextPreference passwordPreference = findPreference("password");


    }

}