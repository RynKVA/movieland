package com.rynkovoi.repository.impl;

import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository()
public class CachedGenreRepository implements GenreRepository {

    private final GenreRepository genreRepository;
    private volatile List<Genre> cache;

    public CachedGenreRepository(@Qualifier("jooqGenreRepository") GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return cache;
    }

    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void updateCache() {
        cache = genreRepository.getAll();
        log.info("Genre cache updated successfully with {} genres.", cache.size());
    }

//    @PostConstruct
//    private void init() {
//        updateCache();
//    }
}
