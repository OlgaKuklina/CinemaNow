package com.example.android.intheaters.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.intheaters.adapters.RecyclerViewAdapter;
import com.example.android.intheaters.utils.IMDBUtils;

import java.io.IOException;

/**
 * Created by olgakuklina on 2016-03-19.
 */
public class FetchIMDBTask extends AsyncTask<String, Void, Integer> {
    private static final String TAG = FetchIMDBTask.class.getSimpleName();
    private static final String BASE_URI = "http://www.imdb.com/title/tt";
    private final FetchIMDBTaskListener fetchIMDBListner;
    private String imdbID;

    public FetchIMDBTask(FetchIMDBTaskListener fetchIMDBListner) {
        this.fetchIMDBListner = fetchIMDBListner;
    }

    @Override
    protected Integer doInBackground(String... params) {
        imdbID = params[0];
        String uri = BASE_URI + params[0];
        Log.v(TAG, "IMDB URI = " + uri);
        try {
            return IMDBUtils.calculateImdbImages(uri);
        } catch (IOException e) {
            Log.e(TAG, "", e);
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);
        Log.v(TAG, "IMDB ID = " + imdbID + " & count = " + count);
        if (count != -1) {
            fetchIMDBListner.onFetchIMDBCompleted(count, imdbID);

        } else {
            fetchIMDBListner.onFetchIMDBFailed(imdbID);
        }

    }
}
