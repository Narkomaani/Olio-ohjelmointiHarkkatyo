package com.example.ht.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ht.HomePageFragment;
import com.example.ht.LoginActivity;
import com.example.ht.MainActivity;
import com.example.ht.R;
import com.example.ht.SearchFavouriteMovieFragment;
import com.example.ht.SearchMovieIMDBFragment;
import com.example.ht.SettingsFragment;
import com.example.ht.User.UserManager;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public androidx.appcompat.widget.Toolbar toolbar;
    public Fragment fragment;
    public NavigationView navigation_view;
    public FragmentManager manager;
    public UserManager userManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

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
        /*manager.beginTransaction()
                .replace(R.id.fragment_window, new HomePageFragment())
                .commit();*/

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = null;
                int itemid = item.getItemId();
                Intent intentSend = new Intent(getApplicationContext(), WeekViewActivity.class);

                // loop to select the fragment
                if ((itemid == R.id.nav_home_page) ) {
                    intentSend.putExtra("keyEE", "homePage");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_search_movie_imdb) {
                    intentSend.putExtra("keyEE", "searchMovie");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_fav_movies) {
                    intentSend.putExtra("keyEE", "favoriteMovie");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_settings) {
                    intentSend.putExtra("keyEE", "settings");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_calendar) {
                    intentSend.putExtra("keyEE", "calendar");
                    startActivity(intentSend);
                    finish();
                } else if ( itemid == R.id.nav_manage_users) {
                    // TODO manage user fragment
                } else if (itemid == R.id.nav_log_out) {
                    userManager.setCurrentUser(null);
                    startActivity(new Intent(EventEditActivity.this, LoginActivity.class));
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

        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}