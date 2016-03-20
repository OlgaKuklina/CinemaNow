package com.example.android.intheaters.asynctasks;

import com.example.android.intheaters.data.MovieDataExt;

/**
 * Created by olgakuklina on 2015-09-02.
 */
public interface FetchMovieListener {

    void onFetchCompleted(MovieDataExt dataExt);

    void onFetchFailed();
}
