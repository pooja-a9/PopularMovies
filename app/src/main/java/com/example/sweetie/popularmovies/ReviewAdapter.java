package com.example.sweetie.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sweetie.popularmovies.data.MovieReview;
import com.example.sweetie.popularmovies.data.MovieTrailer;

/**
 * Created by Sweetie on 9/19/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private MovieReview[] mReviewData;

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        MovieReview review = mReviewData[position];
        TextView comment = holder.mMovieReview;
        TextView author = holder.mMovieReviewAuthor;
        comment.setText(review.getContent());
        author.setText(review.getAuthor());

    }

    @Override
    public int getItemCount() {
        if (null == mReviewData)
            return 0;
        return mReviewData.length;
    }

    public void setReviewData(MovieReview[] mReviewData) {
        this.mReviewData = mReviewData;
        notifyDataSetChanged();
    }


    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieReview;
        public final TextView mMovieReviewAuthor;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieReview = (TextView) itemView.findViewById(R.id.movie_review);
            mMovieReviewAuthor=(TextView) itemView.findViewById(R.id.movie_author);;
        }
    }
}
