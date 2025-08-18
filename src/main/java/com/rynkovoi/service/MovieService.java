package com.rynkovoi.service;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.common.request.MovieRequest;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.type.EnrichmentType;

import java.util.List;

public interface MovieService {

    PageWrapper<MovieDto> getAll(MovieFilter request);

    List<MovieDto> getRandom();

    List<MovieDto> getByGenreId(int genreId);

    MovieResponse getById(long id, CurrencyCode currency, EnrichmentType ... enrichmentTypes);

    MovieDto save(MovieRequest request);

    MovieDto update(long id, MovieRequest request);

    void delete(long id);
}