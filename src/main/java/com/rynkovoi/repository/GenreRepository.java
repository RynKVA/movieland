package com.rynkovoi.repository;

import com.rynkovoi.common.dto.GenreDto;

import java.util.List;

public interface GenreRepository {

    List<GenreDto> findAllDto();
}
