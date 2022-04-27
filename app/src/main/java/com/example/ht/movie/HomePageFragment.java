package com.example.ht.movie;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.ht.R;

public class HomePageFragment extends Fragment {
    private int positionTheatre;
    private TheaterManager tm;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        //spinner for theaters from the homepage

        Spinner theater_spinner = rootView.findViewById((R.id.theater_spinner));

        //Clear problem here
        /*
       tm = TheaterManager.getInstance();

        theater_spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tm.theaterarray()));
        theater_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionTheatre = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        return rootView;
    }


}
