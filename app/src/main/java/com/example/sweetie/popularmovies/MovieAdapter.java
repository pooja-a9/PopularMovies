package com.example.sweetie.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sweetie.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Sweetie on 8/14/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Movie[] mMovieData;


    private final  MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {

        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.movie_item,parent,false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie= mMovieData[position];
        ImageView imageView = holder.mMovieImage;
        TextView movieName=holder.mMovieName;
        if(movie.getPosterPath()!=null) {
            Picasso.with(imageView.getContext()).load(movie.getFullPosterPath()).into(imageView);
            imageView.setVisibility(View.VISIBLE);
            movieName.setVisibility(View.GONE);
        }else{

            imageView.setVisibility(View.GONE);
            movieName.setVisibility(View.VISIBLE);
            movieName.setText(movie.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        if (null == mMovieData)
            return 0;
        return mMovieData.length;
    }

    public void setMovieData(Movie[] mMovieData) {
        this.mMovieData = mMovieData;
        notifyDataSetChanged();
    }



    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final ImageView mMovieImage;
        public final TextView mMovieName;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);

            mMovieImage= (ImageView) itemView.findViewById(R.id.movie_image);
            mMovieName= (TextView) itemView.findViewById(R.id.movie_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieData[adapterPosition];
            mClickHandler.onClick(movie);
        }
    }
}
