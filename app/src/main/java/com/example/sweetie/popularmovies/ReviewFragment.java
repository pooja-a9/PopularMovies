package com.example.sweetie.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sweetie.popularmovies.data.Movie;
import com.example.sweetie.popularmovies.data.MovieReview;
import com.example.sweetie.popularmovies.utilities.MovieJsonUtils;
import com.example.sweetie.popularmovies.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by Sweetie on 9/20/2017.
 */

public class ReviewFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieReview[]> {

    private ReviewAdapter mReviewAdapter;
    public static final String MOVIES_CONTENT_TYPE = "MOVIES_CONTENT_TYPE";

    private int movieId;

    public static final int MOVIE_LOADER_ID = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mReviewAdapter = new ReviewAdapter();

        Bundle bundle = getArguments();
        movieId = bundle.getInt(Movie.MOVIE_ID);
        LoaderManager.LoaderCallbacks<MovieReview[]> callback = this;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString(MOVIES_CONTENT_TYPE, NetworkUtils.MOVIE_REVIEWS);

        int loaderId = MOVIE_LOADER_ID;
        getActivity().getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback).forceLoad();

        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_review, container, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mReviewAdapter);
        return rv;
    }

    @Override
    public Loader<MovieReview[]> onCreateLoader(int id, Bundle args) {
        {

            return new AsyncTaskLoader<MovieReview[]>(getActivity()) {

                MovieReview[] mMovieReviews = null;

                @Override
                protected void onStartLoading() {
                    if (mMovieReviews != null) {
                        deliverResult(mMovieReviews);
                    }
                }

                @Override
                public MovieReview[] loadInBackground() {

                    URL movieRequestUrl = NetworkUtils.buildMovieUrl(NetworkUtils.MOVIE_REVIEWS, movieId);

                    try {
                        String jsonResponse = NetworkUtils
                                .getResponseFromURL(movieRequestUrl);

                        MovieReview[] simpleMovieData = MovieJsonUtils
                                .getMovieReviewsFromJson(jsonResponse);

                        return simpleMovieData;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                public void deliverResult(MovieReview[] data) {
                    mMovieReviews = data;
                    super.deliverResult(data);
                }
            };
        }
    }

    @Override
    public void onLoadFinished(Loader<MovieReview[]> loader, MovieReview[] data) {
        mReviewAdapter.setReviewData(data);
    }

    @Override
    public void onLoaderReset(Loader<MovieReview[]> loader) {

    }


}
