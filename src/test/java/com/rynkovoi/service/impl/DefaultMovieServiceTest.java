package com.rynkovoi.service.impl;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.mapper.MovieMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.properties.MovieRandomProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.web.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultMovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private DefaultMovieService movieService;

    MovieRandomProperties movieRandomProperties;

    DefaultMovieService movieServiceSpy;

    @BeforeEach
    void setUp() {
        movieRandomProperties = new MovieRandomProperties();
        movieRandomProperties.setCountOfMovies(3);
        movieServiceSpy = spy(new DefaultMovieService(movieRepository, movieMapper, movieRandomProperties));
    }

    @Test
    void whenUseGetAllMovies_thenReturnAllMovies() {
        List<Movie> movies = List.of();
        when(movieRepository.getAllMovies()).thenReturn(movies);
        when(movieMapper.toMovieDto(movies)).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> allMovies = movieService.getAllMovies();
        assertEquals(3, allMovies.size());
        assertEquals("Movie 1", allMovies.get(0).getNameNative());
        assertEquals("Movie 2", allMovies.get(1).getNameNative());
        assertEquals("Movie 3", allMovies.get(2).getNameNative());

        verify(movieRepository).getAllMovies();
        verify(movieMapper).toMovieDto(movies);
    }

    @Test
    void whenGetRandomThreeMoviesFromListOfFourMovies_thenReturnThreeRandomMovies() {
        //List of 4 movies
        when(movieServiceSpy.getAllMovies()).thenReturn(ExemplarsCreator.createMovieDtoListWithFourMovies());

        List<MovieDto> randomThreeMovies = movieServiceSpy.getRandomMovies();

        //List of 3 random movies
        assertEquals(3, randomThreeMovies.size());
        verify(movieServiceSpy).getAllMovies();
    }

    @Test
    void whenGetRandomThreeMoviesFromListOfThreeMovies_thenReturnThreeRandomMovies() {
        //List of 3 movies
        when(movieServiceSpy.getAllMovies()).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> randomThreeMovies = movieServiceSpy.getRandomMovies();

        //List of 3 random movies
        assertEquals(3, randomThreeMovies.size());
        verify(movieServiceSpy).getAllMovies();
    }

    @Test
    void whenGetRandomThreeMoviesFromEmptyList_thenReturnEmptyList() {
        //Empty list of movies
        when(movieServiceSpy.getAllMovies()).thenReturn(List.of());

        List<MovieDto> randomThreeMovies = movieServiceSpy.getRandomMovies();

        //Empty list of random movies
        assertTrue(randomThreeMovies.isEmpty());
        verify(movieServiceSpy).getAllMovies();
    }

    @Test
    void whenGetMoviesByGenreId_thenReturnMovieListWithSameGenreId() {
        int genreId = 1;
        List<Movie> movieListWithThreeMoviesWithSameGenreId = ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId();

        when(movieRepository.getMoviesByGenreId(genreId)).thenReturn(movieListWithThreeMoviesWithSameGenreId);
        when(movieMapper.toMovieDto(movieListWithThreeMoviesWithSameGenreId))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> moviesByGenreId = movieService.getMoviesByGenreId(genreId);

        assertEquals(3, moviesByGenreId.size());
        assertEquals("Movie 1", moviesByGenreId.get(0).getNameNative());
        assertEquals("Movie 2", moviesByGenreId.get(1).getNameNative());
        assertEquals("Movie 3", moviesByGenreId.get(2).getNameNative());
        verify(movieRepository).getMoviesByGenreId(genreId);
        verify(movieMapper).toMovieDto(movieListWithThreeMoviesWithSameGenreId);
    }

//    @Test
//    void whenGetSortedMoviesWithNoParameters_thenReturnDefaultAllMovies() {
//        SortRequest request = SortRequest.builder()
//                .sortType(null)
//                .sortOrder(null)
//                .build();
//
//        when(movieServiceSpy.getAllMovies())
//                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());
//
//        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
//
//        assertEquals(3, sortedMovies.size());
//        assertEquals("Movie 1", sortedMovies.get(0).getNameNative());
//        assertEquals("Movie 2", sortedMovies.get(1).getNameNative());
//        assertEquals("Movie 3", sortedMovies.get(2).getNameNative());
//        verify(movieServiceSpy).getAllMovies();
//    }
//
//    @Test
//    void whenGetSortedMoviesWithSortedByNullAndOrderByASC_thenReturnDefaultAllMovies() {
//        SortRequest request = SortRequest.builder()
//                .sortType(null)
//                .sortOrder(SortOrder.ASC)
//                .build();
//        when(movieServiceSpy.getAllMovies())
//                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());
//
//        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
//
//        assertEquals(3, sortedMovies.size());
//        assertEquals("Movie 1", sortedMovies.get(0).getNameNative());
//        assertEquals("Movie 2", sortedMovies.get(1).getNameNative());
//        assertEquals("Movie 3", sortedMovies.get(2).getNameNative());
//        verify(movieServiceSpy).getAllMovies();
//    }
//
//    @Test
//    void whenGetSortedMoviesWithSortedByPriceAndOrderByNull_thenReturnSortedListByDefaultOrderASC() {
//        SortRequest request = SortRequest.builder()
//                .sortType(SortType.PRICE)
//                .sortOrder(null)
//                .build();
//
//        when(movieServiceSpy.getAllMovies())
//                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());
//
//        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
//
//        assertEquals(3, sortedMovies.size());
//        assertEquals(10, sortedMovies.get(0).getPrice());
//        assertEquals(12, sortedMovies.get(1).getPrice());
//        assertEquals(15, sortedMovies.get(2).getPrice());
//        verify(movieServiceSpy).getAllMovies();
//    }
//
//    @Test
//    void whenGetSortedMoviesWithSortedByPriceAndOrderByASC_thenReturnSortedList() {
//        SortRequest request = SortRequest.builder()
//                .sortType(SortType.PRICE)
//                .sortOrder(SortOrder.ASC)
//                .build();
//        when(movieServiceSpy.getAllMovies())
//                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());
//
//        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
//
//        assertEquals(3, sortedMovies.size());
//        assertEquals(10, sortedMovies.get(0).getPrice());
//        assertEquals(12, sortedMovies.get(1).getPrice());
//        assertEquals(15, sortedMovies.get(2).getPrice());
//        verify(movieServiceSpy).getAllMovies();
//    }
//
//    @Test
//    void whenGetSortedMoviesWithSortedByRatingAndOrderByDESC_thenReturnSortedList() {
//        SortRequest request = SortRequest.builder()
//                .sortType(SortType.RATING)
//                .sortOrder(SortOrder.DESC)
//                .build();
//        when(movieRepository.getSortedMovies(request))
//                .thenReturn(ExemplarsCreator.crea());
//
//        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
//
//        assertEquals(3, sortedMovies.size());
//        assertEquals(9.0, sortedMovies.get(0).getRating());
//        assertEquals(8.5, sortedMovies.get(1).getRating());
//        assertEquals(7.5, sortedMovies.get(2).getRating());
//        verify(movieServiceSpy).getAllMovies();
//    }
}