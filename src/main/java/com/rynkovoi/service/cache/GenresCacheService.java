package com.rynkovoi.service.cache;

import com.rynkovoi.exception.GenreNotFoundException;
import com.rynkovoi.mapper.GenresMapper;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.web.dto.GenreDto;
import generated.tables.records.GenresRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenresCacheService {

    private final GenresMapper genresMapper;
    private final GenreRepository genreRepository;
    private final GenreCache cache;

    public List<GenreDto> getAllGenres() {
        validateCache();
        return genresMapper.toGenreDto(cache.getValues());
    }

    public GenreDto getById(int id) {
        validateCache();
        GenresRecord genre = cache.getValues().stream()
                .filter(genresRecord -> genresRecord.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: %s".formatted(id)));
        return genresMapper.toGenreDto(genre);
    }

    public Integer getGenreIdByName(String name) {
        validateCache();
        return cache.getValues().stream()
                .filter(genre -> genre.getName().equalsIgnoreCase(name))
                .map(GenresRecord::getId)
                .findFirst()
                .orElseThrow(() -> new GenreNotFoundException("Genre not found with name: %s".formatted(name)));
    }

    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    protected void updateCache() {
        cache.clear();
        cache.save(genreRepository.getAllGenres());
    }

    protected void validateCache() {
        if (cache.isEmpty()) {
            updateCache();
        }
    }
}