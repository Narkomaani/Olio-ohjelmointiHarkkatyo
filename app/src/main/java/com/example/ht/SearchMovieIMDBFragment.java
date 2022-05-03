package com.example.ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ht.movie.SearchMovieIMDB;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class SearchMovieIMDBFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_searchmovieimdb, container, false);

        Button imdbbutton = rootView.findViewById(R.id.btn_searchimdb);
        ListView listview = rootView.findViewById(R.id.imdbmovielist);
        EditText movieTitle = rootView.findViewById(R.id.searchtitleimdb);
        EditText personName = rootView.findViewById(R.id.searchactordirectorimdb);



        imdbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> imdbMoviesArray = null;

                //quickly done switch that if either one is empty it will choose the full one, or if both have some text it
                //it will choose the title first.
                if (movieTitle.getText().toString().isEmpty() == false) {
                    String givenText = movieTitle.getText().toString();
                    imdbMoviesArray = SearchMovieIMDB.readMovieJson(givenText);
                } else if (personName.getText().toString().isEmpty() == false) {
                    String givenText =personName.getText().toString();
                    imdbMoviesArray = SearchMovieIMDB.readPersonMoviesJson(givenText);
                }


                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, imdbMoviesArray);

                listview.setAdapter(arrayAdapter2);


            }
        });

        return rootView;
    }
}
