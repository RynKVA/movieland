package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.properties.MovieProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.web.dto.MovieDto;
import com.rynkovoi.web.request.SortRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final CommonMapper mapper;
    private final MovieProperties movieProperties;

    @Override
    public List<MovieDto> getAll() {
        return mapper.toMovieDtos(movieRepository.findAll());
    }

    @Override
    public List<MovieDto> getRandom() {
        int count = movieProperties.getRandomCount();
        log.info("Generating {} random movies", count);
        List<MovieDto> allMovies = new ArrayList<>(getAll());
        if (allMovies.size() <= count) {
            return allMovies;
        }
        Collections.shuffle(allMovies);
        List<MovieDto> randomMovies = allMovies.subList(0, count);
        log.info("Getting random movies: {}", mapper.toMovieNames(randomMovies));
        return randomMovies;
    }

    @Override
    public List<MovieDto> getByGenreId(int genreId) {
        return mapper.toMovieDtos(movieRepository.findByGenresId(genreId));
    }

    @Override
    public List<MovieDto> getSortedMovies(SortRequest sortRequest) {
        if (Objects.nonNull(sortRequest.getSortType())) {
            log.info("Sorting movies by: {} with order by {}", sortRequest.getSortType().name(), sortRequest.getOrderType().name());
            Sort sort = Sort.by(Sort.Direction.fromString(sortRequest.getOrderType().name()), sortRequest.getSortType().getName());
            return mapper.toMovieDtos(movieRepository.findAll(sort));
        }
        log.info("No sorting applied");
        return getAll();
    }
}