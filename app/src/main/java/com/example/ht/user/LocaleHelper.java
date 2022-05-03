package com.example.ht.user;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.preference.PreferenceManager;

import com.example.ht.MainActivity;

import java.util.Locale;

public class LocaleHelper {
    private static final String SELECTED_LANGUAGE = "selected_language";

    // the method is used to set the language at runtime
    public static void setLocale(Context context, String language) {
        persist(context, language);
        updateResources(context, language);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    private static void updateResources(Context context, String language) {
        Locale myLocale = new Locale(language);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = myLocale;
        resources.updateConfiguration(conf, dm);
        Intent refresh = new Intent(context, MainActivity.class);
        refresh.putExtra("keyCA", "settings");
        context.startActivity(refresh);
    }
}
