package com.eimovies.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmesService {
    @GET("discover/movie?language=pt-BR")
    Call<FilmesResult> obterFilmesPopulares(@Query("api_key") String chaveApi);

    @GET("movie/top_rated?language=pt-BR")
    Call<FilmesResult> obterTopRatedo(@Query("api_key") String chaveApi);

    @GET("movie/upcoming?language=pt-BR")
    Call<FilmesResult> obterUpComing(@Query("api_key") String chaveApi);

    @GET("movie/now_playing?language=pt-BR")
    Call<FilmesResult> obterPlayingNow(@Query("api_key") String chaveApi);

    @GET("movie/{movie_id}/similar?language=pt-BR")
    Call<FilmesResult> obterSimilar(@Path("movie_id") int movieId, @Query("api_key") String chaveApi);

    @GET("movie/{movie_id}/recommendations?language=pt-BR")
    Call<FilmesResult> obterRecommandations(@Path("movie_id") int movieId, @Query("api_key") String chaveApi);

    @GET("movie/{movie_id}/videos?language=pt-BR")
    Call<TrailerVideoYT> obterYoutubeVideo(@Path("movie_id") int movieId, @Query("api_key") String chaveApi);

    @GET("search/movie?language=pt-BR")
    Call<FilmesResult> obterSearchMovies(@Query("query") String searchData, @Query("api_key") String chaveApi);
}