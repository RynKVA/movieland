package com.rynkovoi.repository.cache;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.repository.jpa.JpaGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CachedGenreRepository extends AbstractCachedRepository<GenreDto> implements GenreRepository {

    private final CommonMapper mapper;
    private final JpaGenreRepository genreRepository;

    @Override
    protected List<GenreDto> fillCache() {
        return mapper.toGenreDtos(genreRepository.findAll());
    }
}
