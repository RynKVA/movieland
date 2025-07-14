package com.rynkovoi.service.impl;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.exception.MovieNotFoundException;
import com.rynkovoi.service.converter.CurrencyConverter;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.properties.MovieProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.common.response.MovieResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final CurrencyConverter currencyConverter;
    private final CommonMapper mapper;
    private final MovieProperties movieProperties;

    @Override
    public List<MovieDto> getAll(MovieFilter movieFilter) {
        if (Objects.nonNull(movieFilter.getSortType())) {
            log.info("Sorting movies by: {} with sort direction {}", movieFilter.getSortType().name(), movieFilter.getSortDirection().name());
            Sort sort = Sort.by(Sort.Direction.fromString(movieFilter.getSortDirection().name()), movieFilter.getSortType().getName());
            return mapper.toMovieDtos(movieRepository.findAll(sort));
        }
        log.info("No sorting applied");
        return mapper.toMovieDtos(movieRepository.findAll(Sort.unsorted()));
    }

    @Override
    public MovieResponse getById(long id, CurrencyCode currencyCode) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        BigDecimal convertedPrice = currencyConverter.convert(movie.getPrice(), currencyCode);
        return mapper.toMovieResponse(movie, convertedPrice);
    }

    @Override
    public List<MovieDto> getRandom() {
        int randomCount = movieProperties.getRandomCount();
        return mapper.toMovieDtos(movieRepository.findRandom(randomCount));
    }

    @Override
    public List<MovieDto> getByGenreId(int genreId) {
        return mapper.toMovieDtos(movieRepository.findByGenresId(genreId));
    }
}