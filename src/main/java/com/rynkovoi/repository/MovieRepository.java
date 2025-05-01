package com.rynkovoi.repository;

import generated.tables.records.MoviesRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static generated.tables.Movies.MOVIES;

@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final DSLContext context;

    public void save(List<MoviesRecord> movies) {
        context.batch(movies.stream()
                        .map(movie -> context.insertInto(MOVIES)
                                .set(MOVIES.NAME_NATIVE, movie.getNameNative())
                                .set(MOVIES.NAME_RUSSIAN, movie.getNameRussian())
                                .set(MOVIES.YEAR_OF_RELEASE, movie.getYearOfRelease())
                                .set(MOVIES.RELEASE_COUNTRY, movie.getReleaseCountry())
                                .set(MOVIES.GENRES, movie.getGenres())
                                .set(MOVIES.DESCRIPTION, movie.getDescription())
                                .set(MOVIES.RATING, movie.getRating())
                                .set(MOVIES.PRICE, movie.getPrice())
                                .set(MOVIES.POSTER, movie.getPoster())
                        ).toList())
                .execute();
    }

    public List<MoviesRecord> getAllMovies() {
        return context.selectFrom(MOVIES)
                .fetch();
    }

    public MoviesRecord getMovieById(long id) {
        return context.selectFrom(MOVIES)
                .where(MOVIES.ID.eq(id))
                .fetchOne();
    }

    public List<MoviesRecord> getMoviesByGenreId(int genreId) {
        return context.selectFrom(MOVIES)
                .where(MOVIES.GENRES.contains(new Integer[]{genreId}))
                .fetch();
    }

    public Long getMovieIdByName(String nameNative) {
        return context.selectFrom(MOVIES)
                .where(MOVIES.NAME_NATIVE.eq(nameNative))
                .fetchAny(MOVIES.ID);
    }
}