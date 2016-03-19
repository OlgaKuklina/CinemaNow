package com.example.android.intheaters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.intheaters.data.MovieData;
//import com.example.android.intheaters.Fragments.InTheatersFragmentInteractionListener;
import com.example.android.intheaters.R;
import com.example.android.intheaters.dummy.DummyContent.DummyItem;
import com.example.android.intheaters.fragments.EndlessRecyclerOnScrollListener;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<MovieData> movieDataList;


    public RecyclerViewAdapter(List<MovieData> items ) {
        movieDataList = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.in_theaters_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(Integer.toString(movieDataList.get(position).id));
        holder.mContentView.setText(movieDataList.get(position).movieName);
    }
    public void add(MovieData res) {
        //finalMoviePosters.add(res);
    }
    public void clearData() {
       // finalMoviePosters.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public MovieData mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
