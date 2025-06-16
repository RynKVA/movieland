package com.rynkovoi.mapper;


import com.rynkovoi.model.Genre;
import com.rynkovoi.web.dto.GenreDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresMapper {

    public GenreDto toGenreDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public List<GenreDto> toGenreDto(List<Genre> genres) {
        return genres.stream()
                .map(genre -> GenreDto.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .toList();
    }
}