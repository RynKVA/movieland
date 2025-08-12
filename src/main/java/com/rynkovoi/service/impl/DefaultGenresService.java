package com.rynkovoi.service.impl;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenresService implements GenreService {

    private final Cache<GenreDto> cache;
    private final GenreRepository genreRepository;
    private final CommonMapper mapper;

    @Override
    public List<GenreDto> getAll() {
        return cache.findAll();
    }

    @Override
    public List<GenreDto> findByMovieId(long movieId) {
        return mapper.toGenreDtos(genreRepository.findByMovieId(movieId));
    }

    @Override
    @Async("enrichmentExecutor")
    public CompletableFuture<List<GenreDto>> findAsyncByMovieId(long movieId) {
        return CompletableFuture.supplyAsync(() -> mapper.toGenreDtos(genreRepository.findByMovieId(movieId)));
    }

    @Override
    public boolean isValid(Set<GenreDto> genres) {
        return genres.stream()
                .allMatch(cache::isExist);
    }
}