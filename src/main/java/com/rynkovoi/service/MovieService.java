package com.rynkovoi.service;

import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.response.MovieResponse;

import java.util.List;

public interface MovieService {

    List<MovieDto> getAll(MovieFilter request);

    List<MovieDto> getRandom();

    List<MovieDto> getByGenreId(int genreId);

    MovieResponse getById(long id, CurrencyCode currency);
}