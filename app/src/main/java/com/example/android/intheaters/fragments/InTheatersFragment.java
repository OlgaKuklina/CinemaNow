package com.example.android.intheaters.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.intheaters.R;
import com.example.android.intheaters.adapters.RecyclerViewAdapter;

public class InTheatersFragment extends Fragment {
    private static final String TAG = InTheatersFragment.class.getSimpleName();
    private EndlessRecyclerOnScrollListener recyclerOnScrollListener;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public InTheatersFragment() {
    }

    @SuppressWarnings("unused")
    public static InTheatersFragment newInstance(int columnCount) {
        InTheatersFragment fragment = new InTheatersFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        adapter = new RecyclerViewAdapter();
        recyclerOnScrollListener = new EndlessRecyclerOnScrollListener(adapter);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        SwipeRefreshLayout view = (SwipeRefreshLayout) inflater.inflate(R.layout.in_theaters_list_fragment, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerOnScrollListener.setLinearLayoutManager(manager);
        recyclerOnScrollListener.setView(view);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(recyclerOnScrollListener);
        view.setOnRefreshListener(recyclerOnScrollListener);
        if(adapter.getItemCount() == 0) {
            recyclerOnScrollListener.loadNextPage();
        }
        return view;
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        recyclerOnScrollListener.cancelAllActiveAsyncTasks();
    }

    @Override
    public void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
        recyclerOnScrollListener.restartAllCanceledTasks();
    }
}