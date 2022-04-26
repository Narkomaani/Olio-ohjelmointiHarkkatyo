package com.example.ht;

public class MovieManager {
    private Movie[] movies;
    private static MovieManager movieManager;


    private MovieManager() {

    }

    public static MovieManager getInstance() {
        return movieManager;
    }

    public Movie[] listMovies() {
        return movies;
    }

    public Movie searchMovie(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return  movie;
            }
        }
        return null;
    }
}
