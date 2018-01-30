package com.example.sweetie.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Sweetie on 9/19/2017.
 */

public class MovieProvider extends ContentProvider {
    public static final int CODE_MOVIE =100;
    public static final int CODE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_POPULARMOVIES, CODE_MOVIE);
        matcher.addURI(authority, MovieContract.PATH_POPULARMOVIES+ "/#", CODE_MOVIE_WITH_ID);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
   }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
      switch (sUriMatcher.match(uri)) {
          case CODE_MOVIE_WITH_ID: {
              String normalizedUtcDateString = uri.getLastPathSegment();
              String[] selectionArguments = new String[]{normalizedUtcDateString};
              cursor = mOpenHelper.getReadableDatabase().query(
                      MovieContract.MoviesEntry.TABLE_NAME,
                      projection,
                      MovieContract.MoviesEntry.COLUMN_MOVIE_ID + " = ?",
                      selectionArguments,
                      null,
                      null, sortOrder);
              break;
          }
              case CODE_MOVIE: {
                  cursor = mOpenHelper.getReadableDatabase().query(
                          MovieContract.MoviesEntry.TABLE_NAME,
                          projection,
                          selection,
                          selectionArgs,
                          null,
                          null,
                          sortOrder);
                break;
              }
          default:
              throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
          cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE: {
                long _id = db.insert(MovieContract.MoviesEntry.TABLE_NAME, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = MovieContract.MoviesEntry.buildMovieUriWithId(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException("We are not implementing getType.");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException("We are not implementing getType.");
    }
}
