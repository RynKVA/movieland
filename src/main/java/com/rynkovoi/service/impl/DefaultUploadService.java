/*
package com.rynkovoi.service.impl;

import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUploadService implements UploadService {

    private final GenreService genreService;
    private final MovieService movieService;

    @Override
    public void parseAndSaveFiles(MultipartFile movies, MultipartFile genres, MultipartFile posters) {
        log.info("Parsing and saving files: movies={}, genres={}, posters={}", movies.getOriginalFilename(),
                genres.getOriginalFilename(), posters.getOriginalFilename());
        genreService.saveParsedGenreNames(genres);
        movieService.saveParsedMovies(movies, posters);
    }
}*/
