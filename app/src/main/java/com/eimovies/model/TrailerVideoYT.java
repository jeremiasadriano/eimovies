package com.eimovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerVideoYT {
    @SerializedName("id")
    private final int id;
    @SerializedName("results")
    private final List<YoutubeResults> results;

    public TrailerVideoYT(int id, List<YoutubeResults> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public List<YoutubeResults> getResults() {
        return results;
    }

    public static class YoutubeResults {
        @SerializedName("key")
        private final String key;

        public YoutubeResults(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
