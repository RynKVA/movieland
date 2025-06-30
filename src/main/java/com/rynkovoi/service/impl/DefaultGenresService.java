package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.GenresMapper;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.web.dto.GenreDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DefaultGenresService implements GenreService {

    private final GenresMapper genresMapper;
    private final GenreRepository genreRepository;

    public DefaultGenresService(GenresMapper genresMapper,
                                @Qualifier("cachedGenreRepository") GenreRepository genreRepository) {
        this.genresMapper = genresMapper;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genresMapper.toGenreDto(genreRepository.getAll());
    }
}