package com.eimovies.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eimovies.client.ApiService;
import com.eimovies.model.FilmeResponse;
import com.eimovies.model.FilmesResult;
import com.eimovies.pages.components.ItemFilmeClickListener;
import com.eimovies.pages.components.ListaFilmesAdapter;
import com.eimovies.pages.components.PlayingNowAdpater;
import com.eimovies.pages.components.TopRatedAdapter;
import com.eimovies.pages.components.UpcomingAdpater;
import com.example.mobilemovies.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesActivity extends AppCompatActivity implements ItemFilmeClickListener {
    private static final String API_KEY = "SECRET";
    private RecyclerView discoverRecycleView, topRatedRecycleView, upcomingRecyclerView, playingNowRecyclerView;
    private TextView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        discover();
        topRated();
        upcoming();
        playingNow();
        startSearchIntent();
    }

    void init() {
        searchBtn = findViewById(R.id.buttonSearch);
        searchBtn.bringToFront();
        topRatedRecycleView = findViewById(R.id.topRated_recycleView);
        discoverRecycleView = findViewById(R.id.discover_filmes);
        upcomingRecyclerView = findViewById(R.id.upcoming_recycleView);
        playingNowRecyclerView = findViewById(R.id.playingNow_recycleView);
    }

    private void discoverConfigRV(List<FilmeResponse> filmeResponses) {
        new ListaFilmesAdapter(this);
        discoverRecycleView.setLayoutManager(new LinearLayoutManager(ListaFilmesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycleView.setAdapter(new ListaFilmesAdapter(ListaFilmesActivity.this, filmeResponses));
    }

    private void discover() {
        ApiService.getConverter().obterFilmesPopulares(API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                FilmesResult filmesResult = response.body();
                List<FilmeResponse> filmeResponses = filmesResult.getFilmeResponses();
                discoverConfigRV(filmeResponses);
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {
                Toast.makeText(ListaFilmesActivity.this, "Erro! problemas de conxeção", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void topRatedConfigRv(List<FilmeResponse> filmeResponses) {
        new TopRatedAdapter(ListaFilmesActivity.this);
        topRatedRecycleView.setLayoutManager(new LinearLayoutManager(ListaFilmesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        topRatedRecycleView.setAdapter(new TopRatedAdapter(ListaFilmesActivity.this, filmeResponses));
    }

    private void topRated() {
        ApiService.getConverter().obterTopRatedo(API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                FilmesResult filmesResult = response.body();
                List<FilmeResponse> filmeResponses = filmesResult.getFilmeResponses();
                topRatedConfigRv(filmeResponses);
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {
                Toast.makeText(ListaFilmesActivity.this, "Erro! problemas de conxeção", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void upcomingConfigRv(List<FilmeResponse> filmeResponses) {
        new UpcomingAdpater(this);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(ListaFilmesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setAdapter(new UpcomingAdpater(ListaFilmesActivity.this, filmeResponses));
    }

    private void upcoming() {
        ApiService.getConverter().obterUpComing(API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                FilmesResult filmesResult = response.body();
                List<FilmeResponse> filmeResponses = filmesResult.getFilmeResponses();
                upcomingConfigRv(filmeResponses);
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {
                Toast.makeText(ListaFilmesActivity.this, "Erro! problemas de conxeção", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void playingNowConfigRV(List<FilmeResponse> filmeResponses) {
        new PlayingNowAdpater(this);
        playingNowRecyclerView.setLayoutManager(new LinearLayoutManager(ListaFilmesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        playingNowRecyclerView.setAdapter(new PlayingNowAdpater(ListaFilmesActivity.this, filmeResponses));
    }

    private void playingNow() {
        ApiService.getConverter().obterPlayingNow(API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                FilmesResult filmesResult = response.body();
                List<FilmeResponse> filmeResponses = filmesResult.getFilmeResponses();
                playingNowConfigRV(filmeResponses);
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {
                Toast.makeText(ListaFilmesActivity.this, "Erro! problemas de conxeção", Toast.LENGTH_LONG).show();
            }
        });
    }

    void startSearchIntent() {
        searchBtn.setOnClickListener(btn -> {
            startActivity(new Intent(ListaFilmesActivity.this, SearchActivity.class));
        });
    }

    @Override
    public void setOnClickListener(FilmeResponse filme) {
        startActivity(new Intent(ListaFilmesActivity.this, DetalhesActivity.class)
                .putExtra(DetalhesActivity.EXTRA_FILMES, filme));
    }
}


