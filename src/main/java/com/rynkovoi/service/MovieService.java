package com.rynkovoi.service;

import com.rynkovoi.web.dto.MovieDto;
import com.rynkovoi.web.request.SortRequest;

import java.util.List;

public interface MovieService {

    List<MovieDto> getAll();

    List<MovieDto> getRandom();

    List<MovieDto> getByGenreId(int genreId);

    List<MovieDto> getSortedMovies(SortRequest request);

}