package com.example.android.intheaters.asynctasks;

/**
 * Created by olgakuklina on 2016-03-19.
 */
public interface FetchIMDBTaskListener {

    void onFetchIMDBCompleted(int count, String imdbID);

    void onFetchIMDBFailed(String imdbID);
}
