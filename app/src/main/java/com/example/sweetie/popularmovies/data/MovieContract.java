package com.example.sweetie.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.Settings;

/**
 * Created by Sweetie on 9/19/2017.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";

    public static final Uri Base_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_POPULARMOVIES = "movies";

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Base_CONTENT_URI.buildUpon().appendPath(PATH_POPULARMOVIES).build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";

        public static Uri buildMovieUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }


}


