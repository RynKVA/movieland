package com.rynkovoi.repository;

import com.rynkovoi.model.Movie;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieRepository {

    List<Movie> findAll();

    List<Movie> findByGenresId(int genreId);

    List<Movie> findAll(Sort sort);
}
