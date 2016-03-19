package com.example.android.intheaters.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.intheaters.adapters.RecyclerViewAdapter;
import com.example.android.intheaters.data.MovieData;
import com.example.android.intheaters.utils.JSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by olgakuklina on 2015-08-29.
 */
public class FetchMovieTask extends AsyncTask<Integer, Void, ArrayList<MovieData>> {
    private static final String POSTER_BASE_URI = "http://image.tmdb.org/t/p/w185";
    private static final String TAG = FetchMovieTask.class.getSimpleName();
    private final RecyclerViewAdapter adapter;
    private final FetchMovieListener fetchListner;

    public FetchMovieTask(RecyclerViewAdapter adapter, FetchMovieListener fetchListner) {
        this.adapter = adapter;
        this.fetchListner = fetchListner;
    }

    @Override
    protected ArrayList<MovieData> doInBackground(Integer... params) {
        ArrayList<MovieData> movieData = new ArrayList<>();
        try {

            JSONObject jObj = JSONLoader.load(params[0]);
            if(jObj == null) {
                Log.w(TAG, "Can not load the data from remote service");
                return null;
            }
            Log.v(TAG,"page" + params[0]);
            JSONArray movieArray = jObj.getJSONArray("results");
            Log.v(TAG, "length:" + movieArray.length());
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.optJSONObject(i);
                String moviePoster = movie.getString("poster_path");
                int movieId = movie.getInt("id");
                MovieData data = new MovieData(POSTER_BASE_URI + moviePoster, movieId);
                movieData.add(data);
                Log.v(TAG, "moviePoster = " + moviePoster);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error ", e);
        }
        return movieData;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieData> moviePosters) {
        super.onPostExecute(moviePosters);
        if (moviePosters != null) {
            for (MovieData res : moviePosters) {
                adapter.add(res);
            }
            adapter.notifyDataSetChanged();
            fetchListner.onFetchCompleted();
        } else {
            fetchListner.onFetchFailed();
        }

    }
}

