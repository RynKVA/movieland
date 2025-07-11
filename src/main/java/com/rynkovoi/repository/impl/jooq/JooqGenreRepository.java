package com.rynkovoi.repository.impl.jooq;


import com.rynkovoi.model.Genre;
import com.rynkovoi.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
@Profile("jooq")
public class JooqGenreRepository implements GenreRepository {

    private final DSLContext context;

    @Override
    public List<Genre> findAll() {
        return context.select(field("id"), field("name"))
                .from(table("public.genres"))
                .fetchInto(Genre.class);
    }
}