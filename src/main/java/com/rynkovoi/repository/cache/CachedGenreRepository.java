package com.rynkovoi.repository.cache;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.repository.jpa.JpaGenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CachedGenreRepository implements GenreRepository {

    private final JpaGenreRepository genreRepository;
    private final CommonMapper mapper;
    private volatile List<GenreDto> cache;


    @Override
    public List<GenreDto> findAllDto() {
        return new ArrayList<>(cache);
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${cache.update.interval-hours}",
            initialDelayString = "${cache.delay}",
            timeUnit = TimeUnit.HOURS)
    void updateCache() {
        cache = mapper.toGenreDtos(genreRepository.findAll());
        log.info("Cache updated: {} items", cache.size());
    }
}
