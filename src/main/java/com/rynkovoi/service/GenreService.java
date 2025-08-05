package com.rynkovoi.service;

import com.rynkovoi.common.dto.GenreDto;

import java.util.List;
import java.util.Set;

public interface GenreService {

    List<GenreDto> getAll();

    List<GenreDto> findByMovieId(long id);

    boolean isValid(Set<GenreDto> genre);
}