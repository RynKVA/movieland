package com.rynkovoi.service.cache;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreCache implements Cache<GenreDto> {

    private final GenreRepository genreRepository;
    private final CommonMapper mapper;
    private volatile List<GenreDto> cache;

    @Override
    public List<GenreDto> findAll() {
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
