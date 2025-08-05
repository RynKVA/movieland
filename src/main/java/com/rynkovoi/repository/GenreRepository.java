package com.rynkovoi.repository;

import com.rynkovoi.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Set<Genre> findByIdIn(List<Integer> ids);

    @Query(value = "SELECT g.* FROM genres g JOIN movies_genres mg ON g.id = mg.genre_id WHERE mg.movie_id = :movieId", nativeQuery = true)
    List<Genre> findByMovieId(long movieId);
}
