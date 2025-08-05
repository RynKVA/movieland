package com.rynkovoi.repository;

import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.custom.CriteriaMovieRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, CriteriaMovieRepository {

    List<Movie> findByGenresId(int genreId);

    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    @EntityGraph(attributePaths = {"genres", "reviews","reviews.user", "releaseCountries"})
    Optional<Movie> findByIdWithNestedData(Long id);

    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    @EntityGraph(attributePaths = {"genres", "releaseCountries"})
    Optional<Movie> findByIdWithGenresAndReleaseCountries(Long id);

    @Query(value = "SELECT * FROM movies ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Movie> findRandom(@Param("limit") int limit);
}
