package com.example.ht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.example.ht.Calendar.CalendarActivity;
import com.example.ht.User.LocaleHelper;
import com.example.ht.User.User;
import com.example.ht.User.UserManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public androidx.appcompat.widget.Toolbar toolbar;
    public Fragment fragment;
    public NavigationView navigation_view;
    public FragmentManager manager;
    public View view;
    public TextView header_text;
    public Context context;

    public UserManager userManager;
    public User user;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public static final String preference_name = "userPreferences";
    public static final String preference_language = "language";
    public static final String preference_darkMode = "dark_mode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // variable set-up
        navigation_view = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        manager = getSupportFragmentManager();
        userManager = UserManager.getUserManager();
        user = userManager.getCurrentUser();
        header_text = findViewById(R.id.headerText); // jostain syystä null
        context = this;
        fragment = new HomePageFragment();

        // giving permission to use the internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Setting up user information
        // header_text.setText(R.string.hello + user.getFirstName() + "!\n"); // EI TOIMI KOSKA HEADER_TEXT ON NULL
        Snackbar.make(drawerLayout, getString(R.string.welcome) + user.getFirstName() + "!", Snackbar.LENGTH_LONG).show();

        // setting up toolbar's navigation menu toggle
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // setting up settings
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(preference_language)) {
                    LocaleHelper.setLocale(MainActivity.this, sharedPreferences.getString(preference_language, "en"));
                    System.out.println("language selected: " + sharedPreferences.getString(preference_language, "en"));
                    editor.putString(preference_language, sharedPreferences.getString(preference_language, "en"));
                } else if (key.equals(preference_darkMode)) {
                    if (sharedPreferences.getBoolean(preference_darkMode, false)) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean(preference_darkMode, true);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putBoolean(preference_darkMode, false);
                    }
                }
                editor.apply();
            }
        });

        // make starting fragment HomePageFragment
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_window) == null ) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_window, new HomePageFragment());
            transaction.commit();
        }

        Intent intentReceive = getIntent();
        String fragmentToOpen = intentReceive.getStringExtra("keyCA");
        System.out.println(fragmentToOpen + "MA");
        if (fragmentToOpen != null) {
            openFragment(fragmentToOpen);
        }

        // navigation bar selection listener
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = null;
                int itemid = item.getItemId();

                // loop to select the fragment
                if ((itemid == R.id.nav_home_page) ) {
                    fragment = new HomePageFragment();
                } else if (itemid == R.id.nav_search_movie_imdb) {
                    fragment = new SearchMovieIMDBFragment();
                } else if (itemid == R.id.nav_fav_movies) {
                    fragment = new SearchFavouriteMovieFragment();
                } else if (itemid == R.id.nav_calendar) {
                    openCalendarActivity();
                } else if (itemid == R.id.nav_settings) {
                    fragment = new SettingsFragment();
                } else if ( itemid == R.id.nav_manage_users) {
                    fragment = new ManageUserFragment();
                } else if (itemid == R.id.nav_log_out) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                // if fragment was found, replace and commit it into the window
                if (fragment != null) {
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_window, fragment);
                    transaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private void openCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    private void openFragment(String fragmentToOpen) {
        System.out.println(fragmentToOpen);
        if (fragmentToOpen.equals("homePage")) {
            fragment = new HomePageFragment();
        } else if (fragmentToOpen.equals("searchMovie")) {
            fragment = new SearchMovieIMDBFragment();
        } else if (fragmentToOpen.equals("favoriteMovie")) {
            fragment = new SearchFavouriteMovieFragment();
        } else if (fragmentToOpen.equals("calendar")) {
            openCalendarActivity();
        } else if (fragmentToOpen.equals("settings")) {
            fragment = new SettingsFragment();
        }

        if (fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_window, fragment);
            transaction.commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}