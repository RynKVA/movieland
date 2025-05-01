package com.rynkovoi.service.cache;

import generated.tables.records.GenresRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreCache {

    private final List<GenresRecord> cache;

    public GenreCache() {
        cache = new ArrayList<>();
    }

    public synchronized void save(List<GenresRecord> values) {
        cache.addAll(values);
    }

    public synchronized void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public List<GenresRecord> getValues() {
        return List.copyOf(cache);
    }
}