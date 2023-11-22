package com.eimovies.pages.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilemovies.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import com.eimovies.model.FilmeResponse;

public class PlayingNowAdpater extends RecyclerView.Adapter<PlayingNowAdpater.SimilarHolder> {
    private Context context;
    private List<FilmeResponse> filmes;
    private static ItemFilmeClickListener itemFilmeClickListener;

    public PlayingNowAdpater(ItemFilmeClickListener itemFilmeClickListener) {
        this.itemFilmeClickListener = itemFilmeClickListener;
    }

    public PlayingNowAdpater(Context context, List<FilmeResponse> filmes) {
        this.context = context;
        this.filmes = filmes;
    }

    @NonNull
    @Override
    public SimilarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarHolder(LayoutInflater.from(this.context).inflate(R.layout.item_playing_now_rv, parent, false));
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
        private TextView title, rating, realiseYear, genre;
        private FilmeResponse filme;

        public SimilarHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.now_poster);
            title = itemView.findViewById(R.id.now_title);
            rating = itemView.findViewById(R.id.now_rate);
            realiseYear = itemView.findViewById(R.id.now_year);
            genre = itemView.findViewById(R.id.now_genre);
            itemView.setOnClickListener(click -> {
                if (itemFilmeClickListener != null) {
                    itemFilmeClickListener.setOnClickListener(filme);
                }
            });
        }

        public void bind(FilmeResponse filme) {
            this.filme = filme;
            title.setText(filme.getOriginalTitle());
            rating.setText(String.valueOf(filme.getVoteAverage()).substring(0, 3).trim());
            realiseYear.setText(String.valueOf(filme.getReleaseDate()).substring(0, 4));
            Picasso.get().load("https://image.tmdb.org/t/p/w780/" + filme.getPosterImage()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    images.setBackground(new BitmapDrawable(images.getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            genre.setText("#" + ListaGeneros.generos(filme).get(0));
        }
    }
}
