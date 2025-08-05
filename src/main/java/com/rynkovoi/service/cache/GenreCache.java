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
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreCache implements Cache<GenreDto> {

    private final GenreRepository genreRepository;
    private final CommonMapper mapper;
    private volatile Map<Integer, GenreDto> cache;

    @Override
    public List<GenreDto> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public boolean isExist(GenreDto genreDto) {
        GenreDto cachedGenre = cache.get(genreDto.getId());
        return cachedGenre != null && cachedGenre.getName().equals(genreDto.getName());
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${cache.update.interval-hours}",
            initialDelayString = "${cache.delay}",
            timeUnit = TimeUnit.HOURS)
    void updateCache() {
        List<GenreDto> genreDtos = mapper.toGenreDtos(genreRepository.findAll());
        cache = genreDtos.stream()
                .collect(Collectors.toMap(GenreDto::getId, Function.identity()));
        log.info("Cache updated: {} items", cache.size());
    }
}
