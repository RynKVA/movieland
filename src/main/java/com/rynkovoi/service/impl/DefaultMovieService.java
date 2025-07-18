package com.rynkovoi.service.impl;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.exception.MovieNotFoundException;
import com.rynkovoi.repository.CriteriaMovieRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final CriteriaMovieRepository criteriaMovieRepository;
    private final MovieRepository movieRepository;
    private final CurrencyConverter currencyConverter;
    private final CommonMapper mapper;
    private final MovieProperties movieProperties;

    @Override
    public PageWrapper<MovieDto> getAll(MovieFilter movieFilter) {
        Page<Movie> movies = criteriaMovieRepository.findAll(movieFilter);
        return new PageWrapper<>(mapper.toMovieDtos(movies.getContent()), movies);
    }

    @Override
    public MovieResponse getById(long id, CurrencyCode currencyCode) {
        Movie movie = movieRepository.findByIdWithNestedData(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        if (currencyCode.equals(CurrencyCode.UAH)) {
            return mapper.toMovieResponse(movie, movie.getPrice());
        }
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