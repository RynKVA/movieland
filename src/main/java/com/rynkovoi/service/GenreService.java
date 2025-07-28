package com.rynkovoi.service;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {

    List<GenreDto> getAll();

    Set<Genre> findAllByIds(List<Integer> ids);
}