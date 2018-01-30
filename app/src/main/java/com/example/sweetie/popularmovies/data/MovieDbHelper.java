package com.example.sweetie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sweetie.popularmovies.data.MovieContract.MoviesEntry;
/**
 * Created by Sweetie on 9/19/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERISON = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERISON);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + MoviesEntry.TABLE_NAME + "(" +
                        MoviesEntry._ID     +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL,"         +
                        MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL,"         +
                        "UNIQUE(" + MoviesEntry.COLUMN_MOVIE_ID+ ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
