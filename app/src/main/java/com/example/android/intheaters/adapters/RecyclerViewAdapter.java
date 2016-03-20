package com.example.android.intheaters.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.intheaters.R;
import com.example.android.intheaters.data.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private final List<MovieData> movieDataList = new ArrayList<>();
    private final Map<String, Integer> movieDataMap = new HashMap<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.in_theaters_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(holder.mIdView.getContext().getString(R.string.movie_id_title)  + " " + Integer.toString(movieDataList.get(position).getMovieId()));
        holder.mContentView.setText(movieDataList.get(position).getMovieName());
        holder.mYear.setText(Integer.toString(movieDataList.get(position).getYear()));
        holder.mReleaseDate.setText(holder.mIdView.getContext().getString(R.string.release_date_title) + " " + movieDataList.get(position).getReleaseDate());
        holder.mAudienceScore.setText(Integer.toString(movieDataList.get(position).getAudienceScore()));
        holder.mSynopsis.setText(movieDataList.get(position).getSynopsis());
        if (movieDataList.get(position).getImdbImageCount() == 0) {
            holder.mImdbImgCount.setText(R.string.no_imdb_images);
        } else if (movieDataList.get(position).getImdbImageCount() == -1) {
            holder.mImdbImgCount.setText(R.string.calculating);
        } else {
            holder.mImdbImgCount.setText(holder.mIdView.getContext().getString(R.string.imdb_img_count) + " " + Integer.toString(movieDataList.get(position).getImdbImageCount()));
        }
        Log.v(TAG, "poster = " + movieDataList.get(position).getMoviePoster());

        Picasso pic = Picasso.with(holder.itemView.getContext());
        pic.load(movieDataList.get(position).getMoviePoster())
                .error(R.drawable.no_poster)
                .into(holder.mPoster);
    }

    public void add(MovieData res) {
        movieDataList.add(res);
        movieDataMap.put(res.getImdbId(), movieDataList.size() - 1);
    }

    public void update(String imdbId, int counter) {
        Integer index = movieDataMap.get(imdbId);
        if (index != null) {
            movieDataList.get(index).setImdbImageCount(counter);
            notifyItemChanged(index);
        }
    }

    public void clearData() {
        movieDataList.clear();
        movieDataMap.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mPoster;
        public final TextView mYear;
        public final TextView mReleaseDate;
        public final TextView mAudienceScore;
        public final TextView mSynopsis;
        public final TextView mImdbImgCount;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content_name);
            mPoster = (ImageView) view.findViewById(R.id.poster);
            mYear = (TextView) view.findViewById(R.id.content_year);
            mReleaseDate = (TextView) view.findViewById(R.id.content_release_date);
            mAudienceScore = (TextView) view.findViewById(R.id.content_audience_score);
            mSynopsis = (TextView) view.findViewById(R.id.content_synopsis);
            mImdbImgCount = (TextView) view.findViewById(R.id.content_imdb_img_count);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
