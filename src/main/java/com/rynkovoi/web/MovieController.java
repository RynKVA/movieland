package com.rynkovoi.web;

import com.rynkovoi.service.MovieService;
import com.rynkovoi.web.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getSortedOrDefaultMovies(@RequestParam(required = false) String sortBy,
                                                   @RequestParam(required = false) String order) {
        log.info("Get sorted movies by {} order be {}", sortBy, order);
        return movieService.getSortedMovies(sortBy, order);
    }

    @GetMapping("/random")
    public List<MovieDto> getRandomThreeMovies() {
        log.info("Get random three movies");
        return movieService.getRandomThreeMovies();
    }

    @GetMapping("genre/{genreId}")
    public List<MovieDto> getMoviesByGenre(@PathVariable int genreId) {
        log.info("Get movies by genre id {}", genreId);
        return movieService.getMoviesByGenreId(genreId);
    }
}