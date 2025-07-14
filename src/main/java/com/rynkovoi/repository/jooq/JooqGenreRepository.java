package com.rynkovoi.repository.jooq;


import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.common.dto.GenreDto;
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
    public List<GenreDto> findAllDto() {
        return context.select(field("id"), field("name"))
                .from(table("public.genres"))
                .fetchInto(GenreDto.class);
    }
}