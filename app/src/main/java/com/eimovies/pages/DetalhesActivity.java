package com.eimovies.pages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eimovies.client.ApiService;
import com.eimovies.model.FilmeResponse;
import com.eimovies.model.FilmesResult;
import com.eimovies.model.TrailerVideoYT;
import com.eimovies.pages.components.GenresAdpater;
import com.eimovies.pages.components.ItemFilmeClickListener;
import com.eimovies.pages.components.ListaGeneros;
import com.eimovies.pages.components.RecommanditionAdpater;
import com.eimovies.pages.components.SimilarAdpater;
import com.example.mobilemovies.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesActivity extends AppCompatActivity implements ItemFilmeClickListener {
    public static final String EXTRA_FILMES = "EXTRA_FILMES";
    private static final String API_KEY = "SECRET";
    private RecyclerView genreRecyclerView, similarRecycleView, recommandationsRecycleView;
    private TextView titleTV, languageTV, realiseTimeTV, ratingTV, sinopseTV;
    private ImageView moviesBackground;
    private YouTubePlayerView youTubePlayerView;
    private FilmeResponse filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        config();
        genreLogic();
        youtubePlayerLogic();
        similarLogic();
        recommandationsLogic();
    }

    void init() {
        titleTV = findViewById(R.id.title);
        languageTV = findViewById(R.id.language);
        realiseTimeTV = findViewById(R.id.realese_Time);

        ratingTV = findViewById(R.id.rating);
        sinopseTV = findViewById(R.id.sinopse);
        moviesBackground = findViewById(R.id.movies_background);
        youTubePlayerView = findViewById(R.id.youtubeContent);
        genreRecyclerView = findViewById(R.id.genreRV);
        similarRecycleView = findViewById(R.id.similarRecycleView);
        recommandationsRecycleView = findViewById(R.id.recommendationREcycleView);
        filme = (FilmeResponse) getIntent().getSerializableExtra(EXTRA_FILMES);
//        Para o yt player
        getLifecycle().addObserver(youTubePlayerView);
    }

    void config() {
        titleTV.setText(filme.getOriginalTitle());
        realiseTimeTV.setText(filme.getReleaseDate().substring(0, 4).trim());
        languageTV.setText(filme.getLanguage());
        ratingTV.setText(String.valueOf(filme.getVoteAverage()).substring(0, 3).trim());
        sinopseTV.setText(filme.getOverview());
        Picasso.get().load("https://image.tmdb.org/t/p/w780/" + filme.getBackgroundPath()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                moviesBackground.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    //    Recycles views
    void genreRecycleViewConfig(LinkedList<String> genreOptions) {
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(DetalhesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        genreRecyclerView.setAdapter(new GenresAdpater(DetalhesActivity.this, genreOptions));
    }

    void similarRecycleViewConfig(List<FilmeResponse> filmePath) {
        new SimilarAdpater(DetalhesActivity.this);
        similarRecycleView.setLayoutManager(new LinearLayoutManager(DetalhesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        similarRecycleView.setAdapter(new SimilarAdpater(DetalhesActivity.this, filmePath));
    }

    void recommandationRecycleViewConfig(List<FilmeResponse> filmePath) {
        new RecommanditionAdpater(DetalhesActivity.this);
        recommandationsRecycleView.setLayoutManager(new LinearLayoutManager(DetalhesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recommandationsRecycleView.setAdapter(new RecommanditionAdpater(DetalhesActivity.this, filmePath));
    }

    void genreLogic() {
        genreRecycleViewConfig(ListaGeneros.generos(filme));
    }

    void youtubePlayerLogic() {
        ApiService.getConverter().obterYoutubeVideo(filme.getId(), API_KEY).enqueue(new Callback<TrailerVideoYT>() {
            @Override
            public void onResponse(Call<TrailerVideoYT> call, Response<TrailerVideoYT> response) {
                TrailerVideoYT trailerVideoYT = response.body();
                List<TrailerVideoYT.YoutubeResults> youtubeResults = trailerVideoYT.getResults();
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);
                        for (TrailerVideoYT.YoutubeResults results : youtubeResults) {
                            youTubePlayer.loadVideo(results.getKey(), 0);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<TrailerVideoYT> call, Throwable t) {

            }
        });

    }

    void similarLogic() {
        ApiService.getConverter().obterSimilar(filme.getId(), API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                if (response.isSuccessful()) {
                    FilmesResult filmesResult = response.body();
                    List<FilmeResponse> filmeResponses = filmesResult.getFilmeResponses();
                    similarRecycleViewConfig(filmeResponses);
                }
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {

            }
        });
    }

    void recommandationsLogic() {
        ApiService.getConverter().obterRecommandations(filme.getId(), API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                if (response.isSuccessful()) {
                    FilmesResult filmesResult = response.body();
                    List<FilmeResponse> filmePath = filmesResult.getFilmeResponses();
                    recommandationRecycleViewConfig(filmePath);
                }
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void setOnClickListener(FilmeResponse filme) {
        Intent intent = new Intent(this, DetalhesActivity.class);
        intent.putExtra(EXTRA_FILMES, filme);
        startActivity(intent);
    }
}