package com.rynkovoi.service.impl;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenresService implements GenreService {

    private final Cache<GenreDto> cache;
    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> getAll() {
        return cache.findAll();
    }

    @Override
    public Set<Genre> findAllByIds(List<Integer> ids) {
        return genreRepository.findByIdIn(ids);
    }
}