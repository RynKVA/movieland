package com.rynkovoi.repository.jpa;

import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface JpaGenreRepository extends JpaRepository<Genre, Integer>, GenreRepository {
}
