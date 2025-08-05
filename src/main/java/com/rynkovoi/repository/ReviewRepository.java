package com.rynkovoi.repository;

import com.rynkovoi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user WHERE r.movie.id = :movieId")
    List<Review> findWithUserByMovieId(Long movieId);
}
