package com.rynkovoi.mapper;


import com.rynkovoi.web.dto.GenreDto;
import generated.tables.records.GenresRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresMapper {

    public GenreDto toGenreDto(GenresRecord genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public List<GenreDto> toGenreDto(List<GenresRecord> genres) {
        return genres.stream()
                .map(genre -> GenreDto.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .toList();
    }
}