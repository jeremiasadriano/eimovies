package com.eimovies.pages.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.eimovies.model.FilmeResponse;

public class ListaGeneros {
    public static LinkedList<String> generos(FilmeResponse filme) {
        List<Integer> genreListIds = filme.getGenre();
        LinkedList<String> genreOptions = new LinkedList<>();
        Map<Integer, String> genreMap = new HashMap<>();
        genreMap.put(28, "Ação");
        genreMap.put(12, "Aventura");
        genreMap.put(16, "Animação");
        genreMap.put(35, "Comédia");
        genreMap.put(80, "Crime");
        genreMap.put(99, "Documentário");
        genreMap.put(18, "Drama");
        genreMap.put(10751, "Familiar");
        genreMap.put(36, "História");
        genreMap.put(27, "Horror");
        genreMap.put(10402, "Música");
        genreMap.put(9648, "Mistério");
        genreMap.put(10749, "Romance");
        genreMap.put(878, "Ficção Científica ");
        genreMap.put(10770, "TV Filme");
        genreMap.put(53, "Thriller");
        genreMap.put(10752, "Guerra");
        genreMap.put(37, "Oeste");

        for (Integer genreIds : genreListIds) {
            for (Integer keys : genreMap.keySet()) {
                if (Objects.equals(genreIds, keys)) {
                    genreOptions.add(genreMap.get(keys));
                }
            }
        }
        return genreOptions;
    }
}
