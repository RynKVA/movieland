package com.rynkovoi.service.impl;

import com.rynkovoi.mapper.MovieMapper;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.MovieService;
import com.rynkovoi.service.parser.FileParser;
import com.rynkovoi.type.SortType;
import com.rynkovoi.web.dto.MovieDto;
import generated.tables.records.MoviesRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.rynkovoi.type.SortType.fromString;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final FileParser fileParser;
    private static final Map<SortType, Comparator<MovieDto>> SORT_COMPARATORS = Map.of(
            SortType.Price, Comparator.comparingDouble(MovieDto::getPrice),
            SortType.Rating, Comparator.comparingDouble(MovieDto::getRating)
    );

    @Override
    public List<MovieDto> getAllMovies() {
        return movieMapper.toMovieDto(movieRepository.getAllMovies());
    }

    @Override
    public List<MovieDto> getRandomThreeMovies() {
        log.info("Generating random movies");
        int randomMoviesCount = 3;
        List<MovieDto> allMovies = new ArrayList<>(getAllMovies());
        if (allMovies.size() <= randomMoviesCount) {
            return allMovies;
        }
        Collections.shuffle(allMovies);
        List<MovieDto> randomMovies = allMovies.subList(0, randomMoviesCount);
        log.info("Getting random movies: {}", movieMapper.toMovieNames(randomMovies));
        return randomMovies;
    }

    @Override
    public List<MovieDto> getMoviesByGenreId(int genreId) {
        return movieMapper.toMovieDto(movieRepository.getMoviesByGenreId(genreId));
    }

    @Override
    public List<MovieDto> getSortedMovies(String sortBy, String orderBy) {
        log.info("Sorting movies by: {} with order by {}", sortBy, orderBy);
        List<MovieDto> allMovies = getAllMovies();
        if (sortBy != null) {
            if (orderBy != null && orderBy.equalsIgnoreCase("desc")) {
                return allMovies.stream()
                        .sorted(SORT_COMPARATORS.get(fromString(sortBy)).reversed())
                        .toList();
            }
            return allMovies.stream()
                    .sorted(SORT_COMPARATORS.get(fromString(sortBy)))
                    .toList();
        }
        log.info("No sorting applied");
        return allMovies;
    }

    @Override
    public void saveParsedMovies(MultipartFile movies, MultipartFile posters) {
        try {
            save(movieMapper.toMovieRecords(fileParser.parseMovieFromFile(movies, posters)));
            log.debug("Saving parsed movies from file: {}", movies.getOriginalFilename());
        } catch (IOException e) {
            log.error("Error parsing file: {}", movies.getOriginalFilename(), e);
            throw new RuntimeException("Error parsing file", e);
        }
    }

    private void save(List<MoviesRecord> movies) {
        movieRepository.save(movies);
    }
}