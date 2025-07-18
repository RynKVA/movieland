package com.rynkovoi.repository;

import com.rynkovoi.model.Movie;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    List<Movie> findAll(Sort sort);

    Optional<Movie> findById(Long id);

    Optional<Movie> findByIdWithNestedData(Long id);

    List<Movie> findByGenresId(int genreId);

    List<Movie> findRandom(int randomCount);
}
