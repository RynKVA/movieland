package com.rynkovoi.repository.jpa;

import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.MovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface JpaMovieRepository extends JpaRepository<Movie, Long>, MovieRepository {
}
