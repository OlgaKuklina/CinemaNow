package com.example.android.intheaters.data;

import java.util.ArrayList;

/**
 * Created by olgakuklina on 2016-03-18.
 */
public class MovieDataExt {

    public final ArrayList<MovieData> data;
    public final int total;

    public MovieDataExt(ArrayList<MovieData> data, int total) {
        this.data = data;
        this.total = total;
    }
}
