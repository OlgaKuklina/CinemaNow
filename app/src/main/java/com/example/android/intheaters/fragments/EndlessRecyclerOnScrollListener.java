package com.example.android.intheaters.fragments;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.intheaters.adapters.RecyclerViewAdapter;
import com.example.android.intheaters.asynctasks.FetchMovieListener;
import com.example.android.intheaters.asynctasks.FetchMovieTask;

/**
 * Created by olgakuklina on 2016-03-18.
 */

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements FetchMovieListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0;
    private boolean loading = true;
    private Activity currentActivity;
    private RecyclerViewAdapter adapter;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private boolean loadingState = false;

    private int current_page = 1;
    private static final int PAGE_SIZE = 15;
    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, Activity currentActivity, RecyclerViewAdapter adapter) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.currentActivity = currentActivity;
        this.adapter = adapter;
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        Log.v(TAG, "firstVisibleItem ="  + firstVisibleItem + "," + "visibleItemCount =" + visibleItemCount + "," + "totalItemCount = " +  totalItemCount);
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            boolean isConnected = checkInternetConnection();
            Log.v(TAG, "Network is" + isConnected);
            if (!isConnected) {
                Log.e(TAG, "Network is not available");

                Toast toast = Toast.makeText(currentActivity.getApplicationContext(),"network_is_not_available_message", Toast.LENGTH_LONG);
                toast.show();
                return;

            }
            onLoadMore(totalItemCount / PAGE_SIZE + 1);

        }

    }

    public void onLoadMore(int current_page) {
        if (!loadingState) {
            FetchMovieTask fetchMovieTask = new FetchMovieTask(adapter, this);
            fetchMovieTask.execute(totalItemCount / PAGE_SIZE + 1);
            loadingState = true;

        }
    }

    @Override
    public void onFetchCompleted() {
        loadingState = false;
    }

    @Override
    public void onFetchFailed() {
        loadingState = false;
        Toast.makeText(currentActivity, "Failed to fetch", Toast.LENGTH_LONG).show();
    }
}