package com.example.sweetie.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sweetie.popularmovies.data.Movie;
import com.example.sweetie.popularmovies.data.MovieTrailer;

/**
 * Created by Sweetie on 9/19/2017.
 */

public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder > {
    private MovieTrailer[] mTrailerData;

    private final TrailerAdapterOnClickHAndler mClickHandler;



    public TrailerAdapter(TrailerAdapterOnClickHAndler mClickHandler) {
        this.mClickHandler = mClickHandler;

    }

    interface TrailerAdapterOnClickHAndler {
        void onClick(MovieTrailer movieTrailer);
    }



    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
        MovieTrailer trailer = mTrailerData[position];
        TextView textView = holder.mMovieTrailer;
        textView.setText(trailer.getTitle());

    }

    @Override
    public int getItemCount() {
        if (null == mTrailerData)
            return 0;
        return mTrailerData.length;
    }

    public void setTrailerData(MovieTrailer[] mTrailerData) {
        this.mTrailerData = mTrailerData;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTrailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mMovieTrailer= (TextView) itemView.findViewById(R.id.movie_trailer);
            mMovieTrailer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer movie = mTrailerData[adapterPosition];
            mClickHandler.onClick(movie);
        }
    }
}
