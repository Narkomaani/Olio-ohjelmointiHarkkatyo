package com.example.ht.movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchMovieIMDB {
    //The job of this class is to take the given movie title/persons name string from SearchMovieIMDBFragment, and show every movie
    //and details of a clicked movie

    private static final String API_key = "k_w4cf6g39/"; //Needs to have / at the end as it will be easier to deal with given end id to the urls.

    public static ArrayList<String> readMovieJson(String urlid) {
        //This class' objective is to bring out the specific movie id out
        String fronturl = "https://imdb-api.com/en/API/SearchMovie/";
        //String urlid = "leon the professional";
        String json = getJSON(fronturl,urlid );
        String movieid = "";
        ArrayList<String> imdbMovieArray = new ArrayList<>();
        //makes the
        if (json != null){
            try { //no catch for empty?
                JSONObject jobject = new JSONObject(json);
                JSONArray jarray = jobject.getJSONArray("results");
                for (int i=0; i<jarray.length(); i++) {
                    System.out.println(jarray.getJSONObject(i).getString("id"));
                    imdbMovieArray.add("\nTitle: " + jarray.getJSONObject(i).getString("title") +"\nDescription: " + jarray.getJSONObject(i).getString("description") +"\nImdb Id: " + jarray.getJSONObject(i).getString("id") +"\n");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return imdbMovieArray;
    }
    public static String readMovieDetailsJson(String givenText) {
        //This functions objective is to bring out the specific movies info, movies id is givenText
        // and it will produce Movie Rating and return it
        String fronturl = "https://imdb-api.com/API/Ratings/";
        String movieid = givenText;
        String json = getJSON(fronturl,movieid);
        String ImdbRating = null;
        if (json != null){
            try {
                JSONObject jobject = new JSONObject(json);

                ImdbRating = jobject.getString("imDb");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ImdbRating;
    }

    public static String readPersonIDJson(String urlid) { // How does static affect if i were to use other methods
        //This functions objective is to bring out the actor id from the returned string
        String frontUrl = "https://imdb-api.com/en/API/SearchName/";


        String json = getJSON(frontUrl,urlid);
        String personID = "";
        //System.out.println("JSON: "+json);
        //makes the
        if (json != null){
            try { //add a catch if the name doesnt produce any results
                //could the run time be shortened?
                JSONObject jobject = new JSONObject(json);
                JSONArray jarray = jobject.getJSONArray("results");
                personID = jarray.getJSONObject(0).getString("id");
                System.out.println(personID);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return personID;
    }
    public static ArrayList<String> readPersonMoviesJson(String givenText){
        //This functions job is to take in the persons id and find the movies assoicated with the name
        String frontUrl = "https://imdb-api.com/en/API/Name/";
        String urlid = readPersonIDJson(givenText);

        ArrayList<String> imdbMovieArray = new ArrayList<>();

        String json = getJSON(frontUrl,urlid );
        if (json != null){
            try {
                JSONObject jobject = new JSONObject(json);
                JSONArray jarray = jobject.getJSONArray("castMovies");
                for (int i=0; i<jarray.length(); i++) {

                    //Cheks if persons role is actor or director
                    String role = jarray.getJSONObject(i).getString("role");
                    if (role.equals("Actor") || role.equals("Director")) {
                        imdbMovieArray.add("\nTitle: " + jarray.getJSONObject(i).getString("title") + "\nYear: " + jarray.getJSONObject(i).getString("year") + "\nImdb Id: " + jarray.getJSONObject(i).getString("id") + "\n");
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return imdbMovieArray;
    }

    //Returns the text from given combination of front part of the url, api key to imdb, and id.
    public static String getJSON(String frontUrl, String urlid) {
        String response = null;
        try{
           URL url = new URL(frontUrl+API_key+urlid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
