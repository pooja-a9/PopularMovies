package com.example.sweetie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sweetie.popularmovies.data.Movie;
import com.example.sweetie.popularmovies.data.MovieContract;
import com.example.sweetie.popularmovies.utilities.MovieJsonUtils;
import com.example.sweetie.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Movie[]> {

    public static final String MOVIE_DATA = "MOVIE_DATA";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    public static final String MOVIES_LIST_TYPE = "MOVIES_LIST_TYPE";
    public static final String FAV_MOVIES = "FAV_MOVIES";


    public static final int MOVIE_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        int numberOfColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mMovieAdapter = new MovieAdapter(this);
        // mMovieAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mMovieAdapter);


        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LoaderManager.LoaderCallbacks<Movie[]> callback = MainActivity.this;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString(MOVIES_LIST_TYPE, NetworkUtils.MOVIE_POPULAR_URL);

        int loaderId = MOVIE_LOADER_ID;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback).forceLoad();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.movie_menu, menu);

        return true;
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {

        final String movieType = args.getString(MOVIES_LIST_TYPE);

        return new AsyncTaskLoader<Movie[]>(this) {

            Movie[] mMovieData = null;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public Movie[] loadInBackground() {
//                String locationQuery = SunshinePreferences
//                        .getPreferredWeatherLocation(MainActivity.this);

                URL movieRequestUrl = NetworkUtils.buildUrl(NetworkUtils.MOVIE_POPULAR_URL);
                boolean isFavMovies = false;
                if (NetworkUtils.TOP_RATED_URL.equals(movieType)) {
                    movieRequestUrl = NetworkUtils.buildUrl(NetworkUtils.TOP_RATED_URL);
                } else if (FAV_MOVIES.equals(movieType)) {
                    isFavMovies = true;
                }

                try {
                    Movie[] simpleMovieData = null;
                    if (isNetworkAvailable() && !isFavMovies) {
                        String jsonResponse = NetworkUtils
                                .getResponseFromURL(movieRequestUrl);

                        simpleMovieData = MovieJsonUtils
                                .getMoviesFromJson(jsonResponse);
                    } else {
                        simpleMovieData = getMoviesFromDB();
                    }


                    return simpleMovieData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Movie[] data) {
                mMovieData = data;
                super.deliverResult(data);
            }

            public Movie[] getMoviesFromDB() {

                String[] projectionColumns = {MovieContract.MoviesEntry.COLUMN_MOVIE_ID, MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE};

                Cursor cursor = getContext().getContentResolver().query(
                        MovieContract.MoviesEntry.CONTENT_URI,
                        projectionColumns,
                        null,
                        null,
                        null);
                if (cursor.getCount() == 0) {
                    return null;
                }
                Movie[] movies = new Movie[cursor.getCount()];


                int i = 0;
                try {
                    while (cursor.moveToNext()) {
                        Movie m = new Movie(cursor.getInt(0), cursor.getString(1));
                        movies[i] = m;
                        i++;
                    }
                } finally {
                    cursor.close();
                }


                return movies;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieAdapter.setMovieData(data);

        if (data == null) {
            showErrorMessage();
        } else {
            showMovieDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {

    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movieData) {
        Context context = this;
        Class destinationClass = MovieDetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Bundle bundle = new Bundle();
        bundle.putSerializable(MOVIE_DATA, movieData);
        intentToStartDetailActivity.putExtras(bundle);

        startActivity(intentToStartDetailActivity);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        if (id == R.id.popular_movies) {
            invalidateData();

            bundle.putString(MOVIES_LIST_TYPE, NetworkUtils.MOVIE_POPULAR_URL);
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle, this).forceLoad();
            return true;
        }
        if (id == R.id.top_rated) {
            invalidateData();
            bundle.putString(MOVIES_LIST_TYPE, NetworkUtils.TOP_RATED_URL);
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle, this).forceLoad();
            return true;
        }
        if (id == R.id.fav_movies) {
            invalidateData();
            bundle.putString(MOVIES_LIST_TYPE, FAV_MOVIES);
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle, this).forceLoad();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void invalidateData() {
        mMovieAdapter.setMovieData(null);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
