package com.rynkovoi.service;

import com.rynkovoi.web.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();
}