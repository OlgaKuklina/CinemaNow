package com.example.android.intheaters.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.android.intheaters.R;
import com.example.android.intheaters.adapters.RecyclerViewAdapter;
import com.example.android.intheaters.asynctasks.FetchMovieListener;
import com.example.android.intheaters.asynctasks.FetchMovieTask;
import com.example.android.intheaters.data.MovieData;

import java.util.ArrayList;
import java.util.List;

public class InTheatersFragment extends Fragment {
    private static final String TAG = InTheatersFragment.class.getSimpleName();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private EndlessRecyclerOnScrollListener mListener;
    private List<MovieData> movieDataList = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;

    public InTheatersFragment() {
    }
    @SuppressWarnings("unused")
    public static InTheatersFragment newInstance(int columnCount) {
        InTheatersFragment fragment = new InTheatersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.in_theaters_list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            LinearLayoutManager manager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(manager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new RecyclerViewAdapter(movieDataList);
            mListener = new EndlessRecyclerOnScrollListener(manager, getActivity(), mAdapter);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addOnScrollListener(mListener);
            mListener.onLoadMore(1);

        }
        return view;
    }



}