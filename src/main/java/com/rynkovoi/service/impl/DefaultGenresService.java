package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.GenresMapper;
import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.cache.GenreCache;
import com.rynkovoi.web.dto.GenreDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

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
        cache.refill(genreRepository.getAllGenres());
    }
}

//    @Override
//    public GenreDto getGenre(int id) {
//        return genresMapper.toGenreDto(cache.getValues().stream()
//                .filter(genre -> genre.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: %s".formatted(id))));
//    }

//    public Integer getGenreIdByName(String name) {
//        return cache.getValues().stream()
//                .filter(genre -> genre.getName().equalsIgnoreCase(name))
//                .map(Genre::getId)
//                .findFirst()
//                .orElseThrow(() -> new GenreNotFoundException("Genre not found with name: %s".formatted(name)));
//    }