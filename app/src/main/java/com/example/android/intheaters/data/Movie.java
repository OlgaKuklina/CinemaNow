package com.example.android.intheaters.data;

/**
 * Created by olgakuklina on 2016-03-17.
 */
public class Movie {
    public  String movieName;
    public  int id;


    public Movie(String movieName, int movieId) {
        this.movieName = movieName;
        this.id = movieId;
    }

    public String getMoviePoster() {
        return movieName;
    }

    public int getMovieId() {
        return id;
    }
}

