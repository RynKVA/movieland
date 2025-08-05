package com.rynkovoi.repository;

import com.rynkovoi.model.ReleaseCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ReleaseCountryRepository extends JpaRepository<ReleaseCountry, Integer> {

    Set<ReleaseCountry> findByIdIn(List<Integer> ids);

    @Query(value = "SELECT rc.* FROM release_countries rc JOIN movies_release_countries mrc ON rc.id = mrc.release_country_id WHERE mrc.movie_id = :movieId",
            nativeQuery = true)
    List<ReleaseCountry> findByMovieId(long movieId);
}
