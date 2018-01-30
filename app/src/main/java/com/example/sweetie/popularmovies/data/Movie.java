package com.example.sweetie.popularmovies.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sweetie on 8/15/2017.
 */

public class Movie implements Serializable {

    private int id;
    private String title;
    private String desc;
    private String posterPath;
    private String voteAvg;
    private String releaseDate;

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    public static final String MOVIE_ID="MOVIE_ID";


    public Movie(int id, String title, String desc, String posterPath, String voteAvg, String releaseDate) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.posterPath = posterPath;
        this.voteAvg = voteAvg;
        this.releaseDate = releaseDate;
    }

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(String voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFullPosterPath() {
        return POSTER_BASE_URL + posterPath;
    }

}
