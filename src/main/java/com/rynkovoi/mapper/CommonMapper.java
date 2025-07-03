package com.rynkovoi.mapper;


import com.rynkovoi.model.Genre;
import com.rynkovoi.model.Movie;
import com.rynkovoi.web.dto.GenreDto;
import com.rynkovoi.web.dto.MovieDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface CommonMapper {

    @Mapping(target = "picturePath", source = "poster")
    MovieDto toMovieDto(Movie movie);

    GenreDto toGenreDto(Genre genres);

    List<MovieDto> toMovieDtos(List<Movie> movies);

    List<GenreDto> toGenreDtos(List<Genre> genres);

    default List<String> toMovieNames(List<MovieDto> movies){
        return movies.stream()
                .map(MovieDto::getNameNative)
                .toList();
    }
}