package com.rynkovoi.service;

import com.rynkovoi.web.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {

    List<MovieDto> getAllMovies();

    List<MovieDto> getRandomThreeMovies();

    List<MovieDto> getMoviesByGenreId(int genreId);

    List<MovieDto> getSortedMovies(String sortType, String order);

    void saveParsedMovies(MultipartFile movies, MultipartFile posters);
}