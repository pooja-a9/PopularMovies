package com.example.sweetie.popularmovies.utilities;

import com.example.sweetie.popularmovies.data.Movie;
import com.example.sweetie.popularmovies.data.MovieReview;
import com.example.sweetie.popularmovies.data.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Sweetie on 8/17/2017.
 */

public class MovieJsonUtils {
    public static Movie[] getMoviesFromJson(String movieJsonString) throws JSONException {


        JSONObject movieJson = new JSONObject(movieJsonString);

        JSONArray results = movieJson.getJSONArray("results");

        Movie[] movieResults = new Movie[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            int movieId = movie.getInt("id");
            String movieTitle = movie.getString("title");
            String movieDesc = movie.getString("overview");
            String moviePoster= movie.getString("poster_path");
            String voteAvg=movie.getString("vote_average");
            String releaseDate=movie.getString("release_date");

            movieResults[i]=new Movie(movieId,movieTitle,movieDesc,moviePoster,voteAvg,releaseDate);
        }


        return movieResults;
    }


    public static MovieTrailer[] getMovieTrailersFromJson(String movieTrailerJsonString) throws JSONException {


        JSONObject movieJson = new JSONObject(movieTrailerJsonString);

        JSONArray results = movieJson.getJSONArray("results");

        MovieTrailer[] movieResults = new MovieTrailer[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject movieTrailer = results.getJSONObject(i);
            String movieTrailerId = movieTrailer.getString("id");
            String movieTrailerTitle = movieTrailer.getString("name");
            String movieTrailerKey = movieTrailer.getString("key");

            movieResults[i]=new MovieTrailer(movieTrailerId,movieTrailerTitle,movieTrailerKey);
        }

        return movieResults;
    }

    public static MovieReview[] getMovieReviewsFromJson(String movieJsonString) throws JSONException {


        JSONObject movieJson = new JSONObject(movieJsonString);

        JSONArray results = movieJson.getJSONArray("results");

        MovieReview[] movieReviews = new MovieReview[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            String movieReviewsId = movie.getString("id");
            String movieReviewsAuthor = movie.getString("author");
            String movieReviewsContent = movie.getString("content");

            movieReviews[i]=new MovieReview(movieReviewsId,movieReviewsAuthor,movieReviewsContent);
        }


        return movieReviews;
    }

}
