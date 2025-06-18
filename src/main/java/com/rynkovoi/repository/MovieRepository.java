package com.rynkovoi.repository;

import com.rynkovoi.model.Movie;
import com.rynkovoi.web.request.SortRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private static final Field<?>[] MOVIE_FIELDS = new Field<?>[]{
            field("id"),
            field("name_native"),
            field("name_russian"),
            field("year_of_release"),
            field("release_country"),
            field("genres", Integer[].class),
            field("description"),
            field("rating"),
            field("price"),
            field("poster")
    };
    private final DSLContext context;

    public List<Movie> getAllMovies() {
        return selectFromMovies().fetchInto(Movie.class);
    }

    public List<Movie> getMoviesByGenreId(int genreId) {
        return selectFromMovies()
                .where(field("genres", Integer[].class).contains(new Integer[]{genreId}))
                .fetchInto(Movie.class);
    }

    public List<Movie> getSortedMovies(SortRequest sortRequest) {
        return selectFromMovies()
                .orderBy(field(sortRequest.getSortType().name()).sort(sortRequest.getSortOrder()))
                .fetchInto(Movie.class);
    }

    private SelectJoinStep<org.jooq.Record> selectFromMovies() {
        return context.select(MOVIE_FIELDS)
                .from(table("public.movies"));
    }
}