package com.rynkovoi.service.cache;

import com.rynkovoi.anotation.Cache;
import com.rynkovoi.model.Genre;

import java.util.List;

@Cache
public class DefaultGenreCache implements GenreCache<Genre> {

    private List<Genre> cache;

    @Override
    public void refill(List<Genre> values) {
        cache = values;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public List<Genre> getValues() {
        return cache;
    }
}