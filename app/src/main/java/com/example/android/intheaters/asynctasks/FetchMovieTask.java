package com.example.android.intheaters.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.intheaters.adapters.RecyclerViewAdapter;
import com.example.android.intheaters.data.MovieData;
import com.example.android.intheaters.data.MovieDataExt;
import com.example.android.intheaters.utils.JSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by olgakuklina on 2015-08-29.
 */
public class FetchMovieTask extends AsyncTask<Integer, Void, MovieDataExt> {
    private static final String TAG = FetchMovieTask.class.getSimpleName();
    private final FetchMovieListener fetchListner;

    public FetchMovieTask(FetchMovieListener fetchListner) {
        this.fetchListner = fetchListner;
    }

    @Override
    protected MovieDataExt doInBackground(Integer... params) {
        ArrayList<MovieData> movieData = new ArrayList<>();
        int total = 0;
        try {

            JSONObject jObj = JSONLoader.load(params[0]);
            if (jObj == null) {
                Log.w(TAG, "Can not load the data from remote service");
                return null;
            }
            Log.v(TAG, "page" + params[0]);
            total = jObj.getInt("total");
            JSONArray movieArray = jObj.getJSONArray("movies");
            Log.v(TAG, "length:" + movieArray.length());
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.optJSONObject(i);
                int movieId = movie.getInt("id");
                String movieName = movie.getString("title");
                int year = movie.getInt("year");
                JSONObject relDates = movie.optJSONObject("release_dates");
                String relDate;
                if (relDates != null) {
                    relDate = relDates.getString("theater");
                } else {
                    relDate = "no release data";
                }
                JSONObject ratings = movie.getJSONObject("ratings");
                int audienceScore = ratings.getInt("audience_score");
                String synopsis = movie.getString("synopsis");
                JSONObject posters = movie.getJSONObject("posters");
                String moviePoster = posters.getString("thumbnail");
                JSONObject alternateIds = movie.optJSONObject("alternate_ids");
                String imdbId;
                if (alternateIds != null) {
                    imdbId = alternateIds.getString("imdb");
                } else {
                    imdbId = null;
                }
                MovieData data = new MovieData(movieName, movieId, moviePoster, year, relDate, audienceScore, synopsis, imdbId);
                movieData.add(data);
                Log.v(TAG, "movieName");
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error ", e);
        }
        return new MovieDataExt(movieData, total);
    }

    @Override
    protected void onPostExecute(MovieDataExt dataExt) {
        super.onPostExecute(dataExt);
        if (dataExt != null) {
            fetchListner.onFetchCompleted(dataExt);
        } else {
            fetchListner.onFetchFailed();
        }

    }
}

