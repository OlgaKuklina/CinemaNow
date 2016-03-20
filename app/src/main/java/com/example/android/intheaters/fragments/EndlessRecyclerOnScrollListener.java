package com.example.android.intheaters.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.intheaters.R;
import com.example.android.intheaters.adapters.RecyclerViewAdapter;
import com.example.android.intheaters.asynctasks.FetchIMDBTask;
import com.example.android.intheaters.asynctasks.FetchIMDBTaskListener;
import com.example.android.intheaters.asynctasks.FetchMovieListener;
import com.example.android.intheaters.asynctasks.FetchMovieTask;
import com.example.android.intheaters.data.MovieDataExt;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olgakuklina on 2016-03-18.
 */

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements FetchMovieListener, FetchIMDBTaskListener, SwipeRefreshLayout.OnRefreshListener{
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager linearLayoutManager;
    private Context currentActivity;
    private RecyclerViewAdapter adapter;
    private int total = Integer.MAX_VALUE;
    private boolean loadingState = false;
    private int current_page = 0;
    private SwipeRefreshLayout view;
    private final Map<String,FetchIMDBTask> fetchIMDBTaskMap = new HashMap<>();

    public EndlessRecyclerOnScrollListener(RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void setLinearLayoutManager(LinearLayoutManager mLinearLayoutManager) {
        this.linearLayoutManager = mLinearLayoutManager;
    }

    public void setView(SwipeRefreshLayout view) {
        this.view = view;
        currentActivity = view.getContext();
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
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
//        Log.v(TAG, "firstVisibleItem =" + firstVisibleItem + "," + "visibleItemCount =" + visibleItemCount + "," + "totalItemCount = " + totalItemCount);
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            boolean isConnected = checkInternetConnection();
            Log.v(TAG, "Network is" + isConnected);
            if (!isConnected) {
                Log.e(TAG, "Network is not available");

                Toast toast = Toast.makeText(currentActivity.getApplicationContext(), R.string.network_is_not_available_message, Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            Log.v(TAG, "onScrolledtotalItemCount = " + totalItemCount + " " + "onScrolledtotal = " + total);
            if (totalItemCount < total) {
                loadNextPage();
            }
        }
    }

    public void loadNextPage() {
        if (!loadingState) {
            Log.e(TAG, "loadNextPage " + current_page);
            FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
            fetchMovieTask.execute(++current_page);
            loadingState = true;
        }
    }

    @Override
    public void onFetchCompleted(MovieDataExt dataExt) {
        Log.v(TAG, "onFetchCompleted");
        this.total = dataExt.total;
        for (int i = 0; i < dataExt.data.size(); i++) {
            String imdbId = dataExt.data.get(i).getImdbId();
            if (imdbId != null) {
                FetchIMDBTask task = new FetchIMDBTask(this);
                task.execute(imdbId);
                fetchIMDBTaskMap.put(imdbId, task);
            } else {
                dataExt.data.get(i).setImdbImageCount(0);
            }
            adapter.add(dataExt.data.get(i));
        }
        adapter.notifyDataSetChanged();
        loadingState = false;
    }

    @Override
    public void onFetchFailed() {
        loadingState = false;
        Toast.makeText(currentActivity, R.string.failed_to_fetch, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchIMDBCompleted(int count, String imdbID) {
        fetchIMDBTaskMap.remove(imdbID);
        adapter.update(imdbID, count);
    }

    @Override
    public void onFetchIMDBFailed(String imdbID) {
        Toast.makeText(currentActivity, currentActivity.getString(R.string.filed_ti_fetch_imdb_data) + imdbID, Toast.LENGTH_LONG).show();
        fetchIMDBTaskMap.remove(imdbID);
        adapter.update(imdbID, 0);
    }

    @Override
    public void onRefresh() {
        Log.v(TAG,"onRefresh");
        adapter.clearData();
        current_page = 0;
        loadNextPage();
        view.setRefreshing(false);
    }

    public void cancelAllActiveAsyncTasks() {
        for(FetchIMDBTask task : fetchIMDBTaskMap.values()) {
            task.cancel(true);
        }
    }

    public void restartAllCanceledTasks() {
        for(String imdbId : fetchIMDBTaskMap.keySet()) {
            if(fetchIMDBTaskMap.get(imdbId).isCancelled()){
                FetchIMDBTask task = new FetchIMDBTask(this);
                task.execute(imdbId);
                fetchIMDBTaskMap.put(imdbId, task);
            }
        }
    }
}