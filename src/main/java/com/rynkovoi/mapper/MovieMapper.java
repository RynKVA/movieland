package com.rynkovoi.mapper;

import com.rynkovoi.model.Movie;
import com.rynkovoi.web.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {

    public List<MovieDto> toMovieDto(List<Movie> movies) {
        return movies.stream()
                .map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .nameNative(movie.getNameNative())
                        .nameRussian(movie.getNameRussian())
                        .yearOfRelease(movie.getYearOfRelease())
                        .rating(movie.getRating())
                        .price(movie.getPrice())
                        .picturePath(movie.getPoster())
                        .build()).toList();
    }

    public List<String> toMovieNames(List<MovieDto> movies) {
        return movies.stream()
                .map(MovieDto::getNameNative)
                .toList();
    }
}