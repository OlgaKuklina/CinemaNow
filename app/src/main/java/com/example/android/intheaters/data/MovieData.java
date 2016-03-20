package com.example.android.intheaters.data;

/**
 * Created by olgakuklina on 2016-03-17.
 */
public class MovieData {
    private final String moviePoster;
    private final String movieName;
    private final int id;
    private final int year;
    private final String releaseDate;
    private final int audienceScore;
    private final String synopsis;
    private final String imdbId;
    private int imdbImageCount;

    public MovieData(String movieName, int movieId, String moviePoster, int year, String releaseDate, int audienceScore, String synopsis, String imdbId) {
        this.movieName = movieName;
        this.id = movieId;
        this.moviePoster = moviePoster;
        this.year = year;
        this.releaseDate = releaseDate;
        this.audienceScore = audienceScore;
        this.synopsis = synopsis;
        this.imdbId = imdbId;
        this.imdbImageCount = -1;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public int getMovieId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getYear() {
        return year;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImdbId() {
        return imdbId;
    }

    public int getImdbImageCount() {
        return imdbImageCount;
    }

    public void setImdbImageCount(int counter) {
        this.imdbImageCount = counter;
    }
}

