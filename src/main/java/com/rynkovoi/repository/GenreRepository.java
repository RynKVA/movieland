package com.rynkovoi.repository;

import com.rynkovoi.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository {

    List<Genre> findAll();

    Set<Genre> findByIdIn(List<Integer> ids);
}
