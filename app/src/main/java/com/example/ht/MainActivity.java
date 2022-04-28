package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.example.ht.movie.HomePageFragment;

import com.example.ht.user.CalendarFragment;
import com.example.ht.user.UserManager;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public androidx.appcompat.widget.Toolbar toolbar;
    public Fragment fragment;
    public NavigationView navigation_view;
    public FragmentManager manager;

    public UserManager userManager;

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

        // giving permission to use the internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // setting up toolbar's navigation menu toggle
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // set default settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences,false);

        // make starting fragment HomePageFragment
        manager.beginTransaction()
                .replace(R.id.fragment_window, new HomePageFragment())
                .commit();

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = null;
                int itemid = item.getItemId();

                // loop to select the fragment
                if ((itemid == R.id.nav_home_page) ) {
                    fragment = new HomePageFragment();
                } else if (itemid == R.id.nav_fav_movies) {
                    // TODO favorite fragment
                } else if (itemid == R.id.nav_calendar) {
                    fragment = new CalendarFragment();
                } else if (itemid == R.id.nav_settings) {
                    fragment = new SettingsFragment();
                } else if ( itemid == R.id.nav_manage_users) {
                    // TODO manage user fragment
                } else if (itemid == R.id.nav_log_out) {
                    userManager.setCurrentUser(null);
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