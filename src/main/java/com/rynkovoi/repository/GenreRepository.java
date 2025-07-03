package com.rynkovoi.repository;

import com.rynkovoi.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();
}
