package com.rynkovoi.web;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.type.SortDirection;
import com.rynkovoi.type.SortType;
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
    public PageWrapper<MovieDto> getAll(@RequestParam(required = false) SortType sortType,
                                        @RequestParam(required = false, defaultValue = "ASC") SortDirection direction,
                                        @RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "10") int size,
                                        @RequestParam(required = false) String searchText) {
        log.info("Get all movies sorted by {} in {} direction, page {}, size {}, searchText '{}'",
                sortType, direction, page, size, searchText);
        return movieService.getAll(MovieFilter.builder()
                .sortType(sortType)
                .sortDirection(direction)
                .page(page)
                .size(size)
                .searchText(searchText)
                .build());
    }

    @GetMapping("/{id}")
    public MovieResponse getById(@PathVariable long id,
                                 @RequestParam(defaultValue = "UAH") CurrencyCode currency) {
        log.info("Get movie by id {}", id);
        return movieService.getById(id, currency);
    }

    @GetMapping("/random")
    public List<MovieDto> getRandom() {
        log.info("Get random movies");
        return movieService.getRandom();
    }

    @GetMapping("genres/{genreId}")
    public List<MovieDto> getByGenre(@PathVariable int genreId) {
        log.info("Get movies by genre id {}", genreId);
        return movieService.getByGenreId(genreId);
    }
}