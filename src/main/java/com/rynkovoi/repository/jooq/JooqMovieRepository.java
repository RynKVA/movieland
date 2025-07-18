package com.rynkovoi.repository.jooq;

import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.common.MovieFilter;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SelectJoinStep;
import org.jooq.SortOrder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
@Profile("jooq")
public class JooqMovieRepository implements MovieRepository {
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

    @Override
    public List<Movie> findByGenresId(int genreId) {
        return selectFromMovies()
                .where(field("genres", Integer[].class).contains(new Integer[]{genreId}))
                .fetchInto(Movie.class);
    }

    @Override
    public List<Movie> findRandom(int randomCount) {
        return List.of();
    }

    //    @Override
    public List<Movie> getSortedMovies(MovieFilter movieFilter) {
        return selectFromMovies()
                .orderBy(field(movieFilter.getSortType().name()).sort(SortOrder.valueOf(movieFilter.getSortDirection().name())))
                .fetchInto(Movie.class);
    }

    @Override
    public List<Movie> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Movie> findByIdWithNestedData(Long id) {
        return Optional.empty();
    }

    private SelectJoinStep<org.jooq.Record> selectFromMovies() {
        return context.select(MOVIE_FIELDS)
                .from(table("public.movies"));
    }
}