package com.rynkovoi.repository.impl.jooq;

import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.web.request.SortRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SelectJoinStep;
import org.jooq.SortOrder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Movie> findAll() {
        return selectFromMovies().fetchInto(Movie.class);
    }

    @Override
    public List<Movie> findByGenresId(int genreId) {
        return selectFromMovies()
                .where(field("genres", Integer[].class).contains(new Integer[]{genreId}))
                .fetchInto(Movie.class);
    }

//    @Override
    public List<Movie> getSortedMovies(SortRequest sortRequest) {
        return selectFromMovies()
                .orderBy(field(sortRequest.getSortType().name()).sort(SortOrder.valueOf(sortRequest.getOrderType().name())))
                .fetchInto(Movie.class);
    }

    @Override
    public List<Movie> findAll(Sort sort) {
        return List.of();
    }

    private SelectJoinStep<org.jooq.Record> selectFromMovies() {
        return context.select(MOVIE_FIELDS)
                .from(table("public.movies"));
    }
}