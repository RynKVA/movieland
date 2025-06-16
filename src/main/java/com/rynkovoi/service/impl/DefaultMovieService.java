package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.MovieMapper;
import com.rynkovoi.properties.MovieRandomProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.web.dto.MovieDto;
import com.rynkovoi.web.request.SortRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieRandomProperties movieRandomProperties;

    @Override
    public List<MovieDto> getAllMovies() {
        return movieMapper.toMovieDto(movieRepository.getAllMovies());
    }

    @Override
    public List<MovieDto> getRandomMovies() {
        int count = movieRandomProperties.getCountOfMovies();
        log.info("Generating {} random movies", count);
        List<MovieDto> allMovies = new ArrayList<>(getAllMovies());
        if (allMovies.size() <= count) {
            return allMovies;
        }
        Collections.shuffle(allMovies);
        List<MovieDto> randomMovies = allMovies.subList(0, count);
        log.info("Getting random movies: {}", movieMapper.toMovieNames(randomMovies));
        return randomMovies;
    }

    @Override
    public List<MovieDto> getMoviesByGenreId(int genreId) {
        return movieMapper.toMovieDto(movieRepository.getMoviesByGenreId(genreId));
    }

    @Override
    public List<MovieDto> getSortedMovies(SortRequest sortRequest) {
        if (sortRequest.getSortType() != null) {
            log.info("Sorting movies by: {} with order by {}", sortRequest.getSortType().name(), sortRequest.getSortOrder().name());
            return movieMapper.toMovieDto(movieRepository.getSortedMovies(sortRequest));
        }
        log.info("No sorting applied");
        return getAllMovies();
    }

//    @Override
//    public void saveParsedMovies(MultipartFile movies, MultipartFile posters) {
//        try {
//            save(movieMapper.toMovieRecords(fileParser.parseMovieFromFile(movies, posters)));
//            log.debug("Saving parsed movies from file: {}", movies.getOriginalFilename());
//        } catch (IOException e) {
//            log.error("Error parsing file: {}", movies.getOriginalFilename(), e);
//            throw new RuntimeException("Error parsing file", e);
//        }
//    }
//
//    private void save(List<MoviesRecord> movies) {
//        movieRepository.save(movies);
//    }
}