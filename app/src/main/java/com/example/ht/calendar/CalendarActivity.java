package com.example.ht.calendar;

import static com.example.ht.calendar.CalendarUtils.daysInMonthArray;
import static com.example.ht.calendar.CalendarUtils.monthYearFromDate;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ht.LoginActivity;
import com.example.ht.MainActivity;
import com.example.ht.R;
import com.example.ht.user.UserManager;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

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
        setContentView(R.layout.activity_calendar);

        // receive information from other variable regarding what fragment to open
        Intent intentReceive = getIntent();
        String fragmentToOpen = intentReceive.getStringExtra("keyWV");
        System.out.println(fragmentToOpen + "CA");
        if (fragmentToOpen != null) {
            Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);
            intentSend.putExtra("keyCA", fragmentToOpen);
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

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = null;
                int itemid = item.getItemId();
                Intent intentSend = new Intent(getApplicationContext(), MainActivity.class);

                // loop to select the fragment
                if ((itemid == R.id.nav_home_page) ) {
                    intentSend.putExtra("keyCA", "homePage");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_search_movie_imdb) {
                    intentSend.putExtra("keyCA", "searchMovie");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_fav_movies) {
                    intentSend.putExtra("keyCA", "favoriteMovie");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_settings) {
                    intentSend.putExtra("keyCA", "settings");
                    startActivity(intentSend);
                    finish();
                } else if ( itemid == R.id.nav_manage_users) {
                    intentSend.putExtra("keyCA", "manageUser");
                    startActivity(intentSend);
                    finish();
                } else if (itemid == R.id.nav_log_out) {
                    userManager.setCurrentUser(null);
                    startActivity(new Intent(CalendarActivity.this, LoginActivity.class));
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
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        Button previousButton = findViewById(R.id.previousButton);
        Button forwardButton = findViewById(R.id.forwardButton);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction(view);
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });

        Button weeklyButton = findViewById(R.id.weeklyButton);
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction();
            }
        });

    }

    private void weeklyAction() {
        Intent intent = new Intent(this, WeekViewActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, (CalendarAdapter.OnItemListener) this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }
}