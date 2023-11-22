package com.eimovies.pages.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.eimovies.model.FilmeResponse;

public class SimilarAdpater extends RecyclerView.Adapter<SimilarAdpater.SimilarHolder> {
    private Context context;
    private List<FilmeResponse> filmes;
    private static ItemFilmeClickListener itemFilmeClickListener;

    public SimilarAdpater(ItemFilmeClickListener itemFilmeClickListener) {
        this.itemFilmeClickListener = itemFilmeClickListener;
    }

    public SimilarAdpater(Context context, List<FilmeResponse> filmes) {
        this.context = context;
        this.filmes = filmes;
    }

    @NonNull
    @Override
    public SimilarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarHolder(LayoutInflater.from(this.context).inflate(R.layout.items_similar_movies_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarHolder holder, int position) {
        holder.bind(filmes.get(position));
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public static class SimilarHolder extends RecyclerView.ViewHolder {
        private ImageView images;
        private TextView title;
        private FilmeResponse filme;

        public SimilarHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.similarImg);
            title = itemView.findViewById(R.id.similarTitle);

            itemView.setOnClickListener(click -> {
                if (itemFilmeClickListener != null) {
                    itemFilmeClickListener.setOnClickListener(filme);
                }
            });
        }

        public void bind(FilmeResponse filme) {
            this.filme = filme;
            title.setText(filme.getOriginalTitle());
            Picasso.get().load("https://image.tmdb.org/t/p/w780/" + filme.getPosterImage()).into(images);
        }
    }
}
