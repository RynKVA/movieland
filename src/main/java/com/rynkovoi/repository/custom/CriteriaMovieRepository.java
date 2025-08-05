package com.rynkovoi.repository.custom;

import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.model.Movie;
import org.springframework.data.domain.Page;

public interface CriteriaMovieRepository {

    Page<Movie> findAll(MovieFilter movieFilter);
}
