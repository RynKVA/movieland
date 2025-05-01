package com.rynkovoi.service.impl;

import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.cache.GenresCacheService;
import com.rynkovoi.service.parser.FileParser;
import com.rynkovoi.web.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreRepository genreRepository;
    private final GenresCacheService genresCacheService;
    private final FileParser fileParser;

    @Override
    public void saveGenres(List<GenreDto> genres) {
        genreRepository.saveGenres(genres);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genresCacheService.getAllGenres();
    }

    @Override
    public GenreDto getGenre(int id) {
        return genresCacheService.getById(id);
    }

    @Override
    public void saveParsedGenreNames(MultipartFile file) {
        try {
            List<String> genreNames = fileParser.parseGenreFromFile(file);
            log.debug("Saving parsed genre names from file: {}", file.getOriginalFilename());
            genreRepository.saveGenreNames(genreNames);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing file with genre names", e);
        }
    }
}