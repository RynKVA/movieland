package com.rynkovoi.repository.jpa;

import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.MovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("jpa")
public interface JpaMovieRepository extends JpaRepository<Movie, Long>, MovieRepository {

    @Query(value = "SELECT * FROM movies ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Movie> findRandom(@Param("limit") int limit);
}
