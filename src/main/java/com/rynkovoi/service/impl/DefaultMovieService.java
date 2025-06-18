package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.MovieMapper;
import com.rynkovoi.properties.MovieRandomProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.web.dto.MovieDto;
import com.rynkovoi.web.request.SortRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MovieMapper movieMapper;
    private final MovieRandomProperties movieRandomProperties;

    @Override
    public List<MovieDto> getAllMovies() {
        return movieMapper.toMovieDto(movieRepository.getAllMovies());
    }

    @Override
    public List<MovieDto> getRandomMovies() {
        int count = movieRandomProperties.getCountOfMovies();
        log.info("Generating {} random movies", count);
        List<MovieDto> allMovies = new ArrayList<>(getAllMovies());
        if (allMovies.size() <= count) {
            return allMovies;
        }
        Collections.shuffle(allMovies);
        List<MovieDto> randomMovies = allMovies.subList(0, count);
        log.info("Getting random movies: {}", movieMapper.toMovieNames(randomMovies));
        return randomMovies;
    }

    @Override
    public List<MovieDto> getMoviesByGenreId(int genreId) {
        return movieMapper.toMovieDto(movieRepository.getMoviesByGenreId(genreId));
    }

    @Override
    public List<MovieDto> getSortedMovies(SortRequest sortRequest) {
        if (Objects.nonNull(sortRequest.getSortType())) {
            log.info("Sorting movies by: {} with order by {}", sortRequest.getSortType().name(), sortRequest.getSortOrder().name());
            return movieMapper.toMovieDto(movieRepository.getSortedMovies(sortRequest));
        }
        log.info("No sorting applied");
        return getAllMovies();
    }
}