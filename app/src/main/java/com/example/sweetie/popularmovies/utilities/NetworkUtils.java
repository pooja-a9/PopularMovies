package com.example.sweetie.popularmovies.utilities;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by Sweetie on 8/17/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String MOVIE_POPULAR_URL = "popular";
    public static final String TOP_RATED_URL = "top_rated";
    public static final String MOVIE_VIDEOS = "videos";
    public static final String MOVIE_REVIEWS = "reviews";
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY_VALUE = "e22ed771df43bd1e002936e7fa25e305";

//https://api.themoviedb.org/3/movie/19404/videos?api_key=e22ed771df43bd1e002936e7fa25e305
    //https://www.youtube.com/watch?v=

    public static URL buildUrl(String movieListType) {
        String baseUrl = "";

        if (MOVIE_POPULAR_URL.equals(movieListType)) {
            baseUrl = MOVIE_URL + MOVIE_POPULAR_URL;
        } else {
            baseUrl = MOVIE_URL + TOP_RATED_URL;
        }

        return getUrl(baseUrl);
    }

    public static URL buildMovieUrl(String movieListType,Integer movieId) {
        String baseUrl = MOVIE_URL +movieId.toString()+"/";

        if (MOVIE_VIDEOS.equals(movieListType)) {
            baseUrl = baseUrl+ MOVIE_VIDEOS;
        } else {
            baseUrl = baseUrl + MOVIE_REVIEWS;
        }

        return getUrl(baseUrl);
    }

    @Nullable
    private static URL getUrl(String baseUrl) {
        Uri builtUri = Uri.parse(baseUrl).buildUpon().appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE).build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromURL(URL url)throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }else
            {
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }



    }

}

