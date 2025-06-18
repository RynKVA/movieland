package com.rynkovoi.repository;


import com.rynkovoi.model.Genre;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class GenreRepository {

    private final DSLContext context;

    public List<Genre> getAllGenres() {
        return context.select(field("id"), field("name"))
                .from(table("public.genres"))
                .fetchInto(Genre.class);
    }
}