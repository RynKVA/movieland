package com.rynkovoi.service.cache;

import com.rynkovoi.anotation.Cache;
import com.rynkovoi.model.Genre;

import java.util.List;

@Cache
public class DefaultGenreCache implements GenreCache<Genre> {

    private volatile List<Genre> cache;

    @Override
    public void refill(List<Genre> values) {
        cache = List.copyOf(values);
    }

    @Override
    public List<Genre> getValues() {
        return List.copyOf(cache);
    }
}