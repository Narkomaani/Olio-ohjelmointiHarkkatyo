package com.example.ht.Calendar;

import static com.example.ht.Calendar.CalendarUtils.daysInWeekArray;
import static com.example.ht.Calendar.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

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
        setContentView(R.layout.activity_week_view);

        Intent intentReceive = getIntent();
        String fragmentToOpen = intentReceive.getStringExtra("keyEE");
        System.out.println(fragmentToOpen + "WV");
        if (fragmentToOpen != null) {
            Intent intentSend = new Intent(getApplicationContext(), CalendarActivity.class);
            intentSend.putExtra("keyWV", fragmentToOpen);
            startActivity(intentSend);
            finish();
        }

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
                Intent intentSend = new Intent(getApplicationContext(), CalendarActivity.class);

                // loop to select the fragment
                if ((itemid == R.id.nav_home_page) ) {
                    /*fragment = new HomePageFragment();
                    finish();*/
                    intentSend.putExtra("keyWV", "homePage");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_search_movie_imdb) {
                    /*fragment = new SearchMovieIMDBFragment();
                    finish();*/
                    intentSend.putExtra("keyWV", "searchMovie");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_fav_movies) {
                    /*fragment = new SearchFavouriteMovieFragment();
                    finish();*/
                    intentSend.putExtra("keyWV", "favoriteMovie");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_settings) {
                    /*fragment = new SettingsFragment();
                    finish();*/
                    intentSend.putExtra("keyWV", "settings");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_calendar) {
                    /*startActivity(new Intent(WeekViewActivity.this, CalendarActivity.class));
                    finish();*/
                    intentSend.putExtra("keyWV", "calendar");
                    startActivity(intentSend);
                    finish();
                } else if ( itemid == R.id.nav_manage_users) {
                    // TODO manage user fragment
                } else if (itemid == R.id.nav_log_out) {
                    userManager.setCurrentUser(null);
                    startActivity(new Intent(WeekViewActivity.this, LoginActivity.class));
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

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTextView);
        eventListView = findViewById(R.id.eventListView);
        setWeekView();

        Button newEventButton = findViewById(R.id.newEventButton);
        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEventAction(view);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, (CalendarAdapter.OnItemListener) this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }
}