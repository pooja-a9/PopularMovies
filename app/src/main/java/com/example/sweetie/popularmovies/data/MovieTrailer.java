package com.example.sweetie.popularmovies.data;

/**
 * Created by Sweetie on 9/19/2017.
 */

public class MovieTrailer {

    private String id;
    private String title;
    private String trailerKey;
    public static final String YOUTUBE_BASE_URL="https://www.youtube.com/watch?v=";

    public MovieTrailer(String id, String title, String trailerKey) {
        this.id = id;
        this.title = title;
        this.trailerKey = trailerKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerURL() {
        return YOUTUBE_BASE_URL+trailerKey;
    }


}
