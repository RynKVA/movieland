package com.rynkovoi.web;

import com.rynkovoi.service.GenreService;
import com.rynkovoi.web.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/genre", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> getAllGenres() {
        log.info("Get all genres");
        return genreService.getAllGenres();
    }

    //For testing updating genre cache
    @PostMapping
    public void saveGenres(@RequestBody List<GenreDto> genres) {
        log.info("Save genres");
        genreService.saveGenres(genres);
    }
}