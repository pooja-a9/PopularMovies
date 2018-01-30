package com.example.sweetie.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetie.popularmovies.data.Movie;
import com.example.sweetie.popularmovies.data.MovieContract;
import com.example.sweetie.popularmovies.data.MovieReview;
import com.example.sweetie.popularmovies.data.MovieTrailer;
import com.example.sweetie.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie mMovie;
    ImageView mMovieImage;
    TextView mMovieTitle;
    TextView mMovieDesc;
    TextView mVoteAvg;
    TextView mReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_DATA);

        mMovieImage = (ImageView) findViewById(R.id.movie_image);
        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mMovieDesc = (TextView) findViewById(R.id.movie_desc);
        mVoteAvg = (TextView) findViewById(R.id.vote_avg);
        mReleaseDate = (TextView) findViewById(R.id.release_date);

        Picasso.with(mMovieImage.getContext()).load(mMovie.getFullPosterPath()).into(mMovieImage);
        mMovieTitle.setText(mMovie.getTitle());
        mMovieDesc.setText(mMovie.getDesc());
        mVoteAvg.setText(mMovie.getVoteAvg());
        mReleaseDate.setText(mMovie.getReleaseDate());


        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putInt(Movie.MOVIE_ID, mMovie.getId());
        //Create Trailer Fragment

        TrailerFragment trailerFragment=new TrailerFragment();

        trailerFragment.setArguments(bundleForLoader);

        //Create Review Fragment
        ReviewFragment reviewFragment=new ReviewFragment();
        reviewFragment.setArguments(bundleForLoader);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.review_fragment, reviewFragment).commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.trailer_fragment, trailerFragment).commit();

    }

    public void saveToFavourites(View view) {

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_ID, mMovie.getId());
        movieValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE, mMovie.getTitle());

        ContentResolver movieContentResolver = this.getContentResolver();

        movieContentResolver.insert(MovieContract.MoviesEntry.CONTENT_URI,movieValues);

        Context context = getApplicationContext();
        CharSequence text = "Added to favourites!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
