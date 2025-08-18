package com.rynkovoi.web;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.common.request.MovieRequest;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.type.EnrichmentType;
import com.rynkovoi.type.SortDirection;
import com.rynkovoi.type.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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
        MovieFilter movieFilter = MovieFilter.builder()
                .sortType(sortType)
                .sortDirection(direction)
                .page(page)
                .size(size)
                .searchText(searchText)
                .build();

        log.info("Get all movies:{}", movieFilter);
        return movieService.getAll(movieFilter);
    }

    @GetMapping("/{id}")
    public MovieResponse getById(@PathVariable long id,
                                 @RequestParam(defaultValue = "UAH") CurrencyCode currency) {

        log.info("Get movie by id {}", id);
        return movieService.getById(id, currency, EnrichmentType.values());
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

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MovieDto save(@RequestBody MovieRequest request) {
        log.info("Save movie: {}", request);
        return movieService.save(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MovieDto update(@PathVariable long id,
                           @RequestBody MovieRequest request) {
        log.info("Update movie id: {} and {}", id, request);
        return movieService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        log.info("Delete movie by id: {}", id);
        movieService.delete(id);
    }
}