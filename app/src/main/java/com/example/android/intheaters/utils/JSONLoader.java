package com.example.android.intheaters.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by olgakuklina on 2015-08-30.
 */
public class JSONLoader {
    private static final String APP_KEY = "hz54u92dhdukkcmxpmyr6rbk";
    private static final String MOVIE_BASE_URI = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json";
    private static final String TAG = JSONLoader.class.getSimpleName();

    public static JSONObject load(int pageIndex) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJsonStr = null;
        try {
            Uri builtUri = Uri.parse(MOVIE_BASE_URI).buildUpon()
                    .appendQueryParameter("apikey", APP_KEY).appendQueryParameter("page", Integer.toString(pageIndex)).build();
            URL url = new URL(builtUri.toString());
            Log.v(TAG, "Built URI " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.v(TAG, "Movie JSON String: " + movieJsonStr);
            JSONObject jObj = new JSONObject(movieJsonStr);
            return jObj;

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

    }
}
