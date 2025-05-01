package com.rynkovoi.repository;


import com.rynkovoi.web.dto.GenreDto;
import generated.tables.records.GenresRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static generated.tables.Genres.GENRES;

@Repository
@RequiredArgsConstructor
public class GenreRepository {

    private final DSLContext context;

    public void saveGenres(List<GenreDto> genres) {
        context.batch(genres.stream()
                        .map(genre -> context.insertInto(GENRES)
                                .set(GENRES.NAME, genre.getName()))
                        .toList())
                .execute();
    }

    public List<GenresRecord> getAllGenres() {
        return context.selectFrom(GENRES)
                .fetch();
    }

    public void saveGenreNames(List<String> genreNames) {
        context.batch(genreNames.stream()
                        .map(genreName -> context.insertInto(GENRES)
                                .set(GENRES.NAME, genreName))
                        .toList())
                .execute();
    }
}