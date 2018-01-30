package com.example.sweetie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.sweetie.popularmovies.data.Movie;
import com.example.sweetie.popularmovies.data.MovieTrailer;
import com.example.sweetie.popularmovies.utilities.MovieJsonUtils;
import com.example.sweetie.popularmovies.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by Sweetie on 9/20/2017.
 */

public class TrailerFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieTrailer[]>, TrailerAdapter.TrailerAdapterOnClickHAndler {
    private TrailerAdapter trailerAdapter;
    public static final String MOVIES_CONTENT_TYPE = "MOVIES_CONTENT_TYPE";

    private int movieId;

    public static final int MOVIE_LOADER_ID = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        trailerAdapter = new TrailerAdapter(this);
        Bundle bundle = getArguments();
        movieId = bundle.getInt(Movie.MOVIE_ID);

        LoaderManager.LoaderCallbacks<MovieTrailer[]> callback = this;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString(MOVIES_CONTENT_TYPE, NetworkUtils.MOVIE_REVIEWS);

        int loaderId = MOVIE_LOADER_ID;
        getActivity().getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback).forceLoad();

        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_trailer, container, false);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(trailerAdapter);
        return rv;
    }

    @Override
    public Loader<MovieTrailer[]> onCreateLoader(int id, Bundle args) {
        {

            return new AsyncTaskLoader<MovieTrailer[]>(getActivity()) {

                MovieTrailer[] mMovieTrailers = null;

                @Override
                protected void onStartLoading() {
                    if (mMovieTrailers != null) {
                        deliverResult(mMovieTrailers);
                    }
                }

                @Override
                public MovieTrailer[] loadInBackground() {

                    URL movieRequestUrl = NetworkUtils.buildMovieUrl(NetworkUtils.MOVIE_VIDEOS, movieId);

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromURL(movieRequestUrl);

                        MovieTrailer[] simpleMovieData = MovieJsonUtils.getMovieTrailersFromJson(jsonResponse);

                        return simpleMovieData;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                public void deliverResult(MovieTrailer[] data) {
                    mMovieTrailers = data;
                    super.deliverResult(data);
                }
            };
        }
    }

    @Override
    public void onLoadFinished(Loader<MovieTrailer[]> loader, MovieTrailer[] data) {
        trailerAdapter.setTrailerData(data);
    }

    @Override
    public void onLoaderReset(Loader<MovieTrailer[]> loader) {

    }

    @Override
    public void onClick(MovieTrailer movieTrailer) {
        Context context = getContext();
        Class destinationClass = MovieDetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Intent lVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieTrailer.getTrailerURL()));
        startActivity(lVideoIntent);
    }
}
