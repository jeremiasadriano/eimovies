package com.eimovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FilmeResponse implements Serializable {
    @SerializedName("backdrop_path")
    private final String backgroundPath;
    @SerializedName("original_title")
    private final String originalTitle;
    @SerializedName("original_language")
    private final String language;
    @SerializedName("id")
    private final int id;
    @SerializedName("genre_ids")
    private final List<Integer> genre;
    private final String overview;
    @SerializedName("vote_average")
    private final double voteAverage;
    @SerializedName("poster_path")
    private final String posterImage;
    @SerializedName("release_date")
    private final String releaseDate;

    public FilmeResponse(String backgroundPath, String originalTitle, int id, String overview, double voteAverage, String posterImage, String releaseDate, String language, List<Integer> genre) {
        this.backgroundPath = backgroundPath;
        this.originalTitle = originalTitle;
        this.id = id;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterImage = posterImage;
        this.releaseDate = releaseDate;
        this.language = language;
        this.genre = genre;
    }


    public String getLanguage() {
        return language;
    }

    public List<Integer> getGenre() {
        return genre;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
