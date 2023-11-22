package com.eimovies.pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemovies.R;

import java.util.List;

import com.eimovies.client.ApiService;
import com.eimovies.model.FilmeResponse;
import com.eimovies.model.FilmesResult;
import com.eimovies.pages.components.ItemFilmeClickListener;
import com.eimovies.pages.components.SearchResultsAdpater;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultSearch extends AppCompatActivity implements ItemFilmeClickListener {

    public static final String EXTRA_FILMES = "EXTRAS";
    private static final String API_KEY = "b23974423f00f9267d31e75c8b937c2e";
    private RecyclerView searchResultsRV;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        init();
        searchLogic();
    }

    void init() {
        searchQuery = getIntent().getStringExtra(EXTRA_FILMES);
        searchResultsRV = findViewById(R.id.search_results_recycleView);
    }

    void SearchResultRVConfig(List<FilmeResponse> filmeResponses) {
        new SearchResultsAdpater(ResultSearch.this);
        searchResultsRV.setLayoutManager(new GridLayoutManager(ResultSearch.this, 2));
        searchResultsRV.setAdapter(new SearchResultsAdpater(ResultSearch.this, filmeResponses));
    }

    void searchLogic() {
        ApiService.getConverter().obterSearchMovies(searchQuery, API_KEY).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                FilmesResult filmesResult = response.body();
                List<FilmeResponse> filmeResponses = filmesResult.getFilmeResponses();
                SearchResultRVConfig(filmeResponses);
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {
                Toast.makeText(ResultSearch.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setOnClickListener(FilmeResponse filme) {
        startActivity(new Intent(this, DetalhesActivity.class)
                .putExtra(DetalhesActivity.EXTRA_FILMES, filme));
    }
}