package com.eimovies.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilemovies.R;
import com.google.android.material.textfield.TextInputEditText;

public class SearchActivity extends AppCompatActivity {
    private TextInputEditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        searchLogic();
    }

    void init() {
        inputSearch = findViewById(R.id.searchInput);
    }

    void searchLogic() {
        inputSearch.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                Intent intent = new Intent(SearchActivity.this, ResultSearch.class);
                intent.putExtra(ResultSearch.EXTRA_FILMES, inputSearch.getText().toString().trim());
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}