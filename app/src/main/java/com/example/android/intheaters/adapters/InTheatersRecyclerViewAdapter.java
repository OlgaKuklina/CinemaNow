package com.example.android.intheaters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.intheaters.data.Movie;
//import com.example.android.intheaters.Fragments.InTheatersFragmentInteractionListener;
import com.example.android.intheaters.R;
import com.example.android.intheaters.dummy.DummyContent.DummyItem;

import java.util.List;

/**
// * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
// * specified {@link \\\\InTheatersFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class InTheatersRecyclerViewAdapter extends RecyclerView.Adapter<InTheatersRecyclerViewAdapter.ViewHolder> {

    private final List<Movie> movieList;
    //private final InTheatersFragmentInteractionListener mListener;

    public InTheatersRecyclerViewAdapter(List<Movie> items /*InTheatersFragmentInteractionListener listener*/) {
        movieList = items;
      //  mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.in_theaters_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(Integer.toString(movieList.get(position).id));
        holder.mContentView.setText(movieList.get(position).movieName);

        holder.mIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Movie mItem;

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
