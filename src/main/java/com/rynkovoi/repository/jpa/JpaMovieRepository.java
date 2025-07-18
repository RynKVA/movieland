package com.rynkovoi.repository.jpa;

import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.MovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
public interface JpaMovieRepository extends JpaRepository<Movie, Long>, MovieRepository {

    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    @EntityGraph(attributePaths = {"genres", "reviews","reviews.user", "releaseCountries"})
    Optional<Movie> findByIdWithNestedData(Long id);

    @Query(value = "SELECT * FROM movies ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Movie> findRandom(@Param("limit") int limit);
}
