package com.rynkovoi.mapper;

import com.rynkovoi.web.dto.MovieDto;
import com.rynkovoi.web.dto.ParsedMovie;
import generated.tables.records.MoviesRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {

    public MovieDto toMovieDto(MoviesRecord movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .nameNative(movie.getNameNative())
                .nameRussian(movie.getNameRussian())
                .yearOfRelease(movie.getYearOfRelease())
                .rating(movie.getRating())
                .price(movie.getPrice())
                .picturePath(movie.getPoster())
                .build();
    }

    public List<MovieDto> toMovieDto(List<MoviesRecord> movies) {
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

    public List<MoviesRecord> toMovieRecords(List<ParsedMovie> parsedMovies) {
        return parsedMovies.stream()
                .map(parsedMovie -> new MoviesRecord()
                        .setNameRussian(parsedMovie.getNameRussian())
                        .setNameNative(parsedMovie.getNameNative())
                        .setYearOfRelease(parsedMovie.getYearOfRelease())
                        .setReleaseCountry(parsedMovie.getReleaseCountry())
                        .setGenres((parsedMovie.getGenres()))
                        .setDescription(parsedMovie.getDescription())
                        .setRating(parsedMovie.getRating())
                        .setPrice(parsedMovie.getPrice())
                        .setPoster(parsedMovie.getPicturePath()))
                .toList();
    }

    public List<String> toMovieNames(List<MovieDto> movies) {
        return movies.stream()
                .map(MovieDto::getNameNative)
                .toList();
    }
}