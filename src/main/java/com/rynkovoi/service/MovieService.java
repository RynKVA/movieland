package com.rynkovoi.service;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.common.request.AddMovieRequest;
import com.rynkovoi.common.request.UpdateMovieRequest;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.type.CurrencyCode;

import java.util.List;

public interface MovieService {

    PageWrapper<MovieDto> getAll(MovieFilter request);

    List<MovieDto> getRandom();

    List<MovieDto> getByGenreId(int genreId);

    MovieResponse getById(long id, CurrencyCode currency);

    MovieDto save(AddMovieRequest request);

    MovieDto update(long id,UpdateMovieRequest request);
}