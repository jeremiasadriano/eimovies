package com.eimovies.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmesResult {
    @SerializedName("results")
    private final List<FilmeResponse> filmeResponses;

    public FilmesResult(List<FilmeResponse> filmeResponses) {
        this.filmeResponses = filmeResponses;
    }

    public List<FilmeResponse> getFilmeResponses() {
        return filmeResponses;
    }
}





