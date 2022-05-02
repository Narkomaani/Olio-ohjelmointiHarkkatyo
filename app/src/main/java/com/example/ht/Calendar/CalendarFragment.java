package com.example.ht.Calendar;

import static android.app.ProgressDialog.show;

import static com.example.ht.Calendar.CalendarUtils.daysInMonthArray;
import static com.example.ht.Calendar.CalendarUtils.monthYearFromDate;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ht.R;

import java.time.LocalDate;
import java.util.ArrayList;


public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        //initWidgets();
        calendarRecyclerView = rootView.findViewById(R.id.calendarRecyclerView);
        monthYearText = rootView.findViewById(R.id.monthYearTextView);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        Button previousButton = rootView.findViewById(R.id.previousButton);
        Button forwardButton = rootView.findViewById(R.id.forwardButton);

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

        Button weeklyButton = rootView.findViewById(R.id.weeklyButton);
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction(view);
            }
        });
        
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }



    /*private void initWidgets() {
        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.monthYearTextView);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        if(dayText.equals("")) {
            String message = "Selected date " + dayText + " " + monthYearFromDate(CalendarUtils.selectedDate);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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

    public void weeklyAction(View view) {
        //tää ei toimi help
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment weekViewFragment = new WeekViewFragment();
        ft.replace(R.id.parentView, weekViewFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
