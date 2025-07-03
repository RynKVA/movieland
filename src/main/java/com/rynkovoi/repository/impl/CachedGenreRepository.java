package com.rynkovoi.repository.impl;

import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class CachedGenreRepository implements GenreRepository {

    private final GenreRepository genreRepository;
    private volatile List<Genre> cache;

    @Override
    public List<Genre> findAll() {
        return new ArrayList<>(cache);
    }

    @PostConstruct
    @Scheduled(fixedRateString = "#{${genre.cache.update.interval-hours}*3600000}")
    void updateCache() {
        cache = genreRepository.findAll();
        log.info("Genre cache updated successfully with {} genres.", cache.size());
    }
}
