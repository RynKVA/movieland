package com.rynkovoi.service.impl;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.common.request.MovieRequest;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.exception.MovieNotFoundException;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.properties.MovieProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.enricher.MovieEnrichmentService;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.service.cache.MovieCache;
import com.rynkovoi.service.converter.CurrencyConverter;
import com.rynkovoi.service.validator.MovieRequestValidator;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.type.EnrichmentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final CurrencyConverter currencyConverter;
    private final CommonMapper mapper;
    private final MovieProperties movieProperties;
    private final MovieEnrichmentService enrichmentService;
    private final MovieRequestValidator movieRequestValidator;
    private final MovieCache movieCache;

    @Override
    public PageWrapper<MovieDto> getAll(MovieFilter movieFilter) {
        Page<Movie> movies = movieRepository.findAll(movieFilter);
        return new PageWrapper<>(mapper.toMovieDtos(movies.getContent()), movies);
    }

    @Override
    public MovieDto save(MovieRequest request) {
        movieRequestValidator.validateMovieRequest(request);
        Movie savedMovie = movieRepository.save(mapper.toMovie(request));
        return mapper.toMovieDto(savedMovie);
    }

    @Override
    @Transactional
    public MovieDto update(long id, MovieRequest request) {
        movieRequestValidator.validateMovieRequest(request);
        Movie movie = movieRepository.findByIdWithGenresAndReleaseCountries(id)
                .orElseThrow(() -> new MovieNotFoundException(id));


        mapper.update(request, movie);
        movieCache.update(id, mapper.toMovieResponse(movie, movie.getPrice()));

        return mapper.toMovieDto(movie);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        movieRepository.delete(movie);
        log.info("Movie with id {} deleted", id);
    }

    @Override
    public MovieResponse getById(long id, CurrencyCode currencyCode, EnrichmentType... enrichmentTypes) {
        MovieResponse cachedMovieResponse = movieCache.get(id);
        convertPrice(cachedMovieResponse, currencyCode);
        return cachedMovieResponse;
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

    private void convertPrice(MovieResponse movieResponse, CurrencyCode currencyCode) {
        if (!currencyCode.equals(CurrencyCode.UAH)) {
            BigDecimal convertedPrice = currencyConverter.convert(movieResponse.getPrice(), currencyCode);
            movieResponse.setPrice(convertedPrice);
        }
    }

}