package com.rynkovoi.service;

import com.rynkovoi.common.dto.GenreDto;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface GenreService {

    List<GenreDto> getAll();

    List<GenreDto> findByMovieId(long id);

    CompletableFuture<List<GenreDto>> findAsyncByMovieId(long movieId);

    boolean isValid(Set<GenreDto> genre);
}