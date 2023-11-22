package com.eimovies.pages.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemovies.R;

import java.util.LinkedList;

public class GenresAdpater extends RecyclerView.Adapter<GenresAdpater.GenresHolder> {
    private Context context;
    private LinkedList<String> genresList;

    public GenresAdpater(Context context, LinkedList<String> genresList) {
        this.context = context;
        this.genresList = genresList;
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenresHolder(LayoutInflater.from(context).inflate(R.layout.items_genres_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder holder, int position) {
        holder.genre.setText(genresList.get(position));
    }

    @Override
    public int getItemCount() {
        return genresList.size();
    }

    public static class GenresHolder extends RecyclerView.ViewHolder {
        private TextView genre;

        public GenresHolder(@NonNull View itemView) {
            super(itemView);
            genre = itemView.findViewById(R.id.genre);
        }
    }
}
