package com.rynkovoi.mapper;

import com.rynkovoi.model.Movie;
import com.rynkovoi.web.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {

    public MovieDto toMovieDto(Movie movie) {
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

//    public List<Movie> toMovieRecords(List<ParsedMovie> parsedMovies) {
//        return parsedMovies.stream()
//                .map(parsedMovie -> new Movie()
//                        .setNameRussian(parsedMovie.getNameRussian())
//                        .setNameNative(parsedMovie.getNameNative())
//                        .setYearOfRelease(parsedMovie.getYearOfRelease())
//                        .setReleaseCountry(parsedMovie.getReleaseCountry())
//                        .setGenres((parsedMovie.getGenres()))
//                        .setDescription(parsedMovie.getDescription())
//                        .setRating(parsedMovie.getRating())
//                        .setPrice(parsedMovie.getPrice())
//                        .setPoster(parsedMovie.getPicturePath()))
//                .toList();
//    }

    public List<String> toMovieNames(List<MovieDto> movies) {
        return movies.stream()
                .map(MovieDto::getNameNative)
                .toList();
    }
}