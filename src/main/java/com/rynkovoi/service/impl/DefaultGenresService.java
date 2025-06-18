package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.GenresMapper;
import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.cache.GenreCache;
import com.rynkovoi.web.dto.GenreDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenresService implements GenreService {

    private final GenresMapper genresMapper;
    private final GenreRepository genreRepository;
    private final GenreCache<Genre> cache;

    @Override
    public List<GenreDto> getAllGenres() {
        return genresMapper.toGenreDto(cache.getValues());
    }

    @PostConstruct
    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    private void updateCache() {
        try {
            List<Genre> allGenres = genreRepository.getAllGenres();
            cache.refill(allGenres);
            log.info("Genre cache updated successfully with {} genres.", allGenres.size());
        } catch (Exception e) {
            log.error("Failed to update genre cache: {}", e.getMessage(), e);
        }
    }
}