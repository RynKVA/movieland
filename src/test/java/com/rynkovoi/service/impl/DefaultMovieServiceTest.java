package com.rynkovoi.service.impl;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.properties.MovieProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.type.OrderType;
import com.rynkovoi.type.SortType;
import com.rynkovoi.web.dto.MovieDto;
import com.rynkovoi.web.request.SortRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultMovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CommonMapper mapper;

    @InjectMocks
    private DefaultMovieService movieService;

    MovieProperties movieProperties;

    DefaultMovieService movieServiceSpy;

    @BeforeEach
    void setUp() {
        movieProperties = new MovieProperties();
        movieProperties.setRandomCount(3);
        movieServiceSpy = spy(new DefaultMovieService(movieRepository, mapper, movieProperties));
    }

    @Test
    void whenUseGetAllMovies_thenReturnAll() {
        List<Movie> movies = List.of();
        when(movieRepository.findAll()).thenReturn(movies);
        when(mapper.toMovieDtos(movies)).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> allMovies = movieService.getAll();
        assertEquals(3, allMovies.size());
        assertEquals("Movie 1", allMovies.get(0).getNameNative());
        assertEquals("Movie 2", allMovies.get(1).getNameNative());
        assertEquals("Movie 3", allMovies.get(2).getNameNative());

        verify(movieRepository).findAll();
        verify(mapper).toMovieDtos(movies);
    }

    @Test
    void whenGetRandomThreeMoviesFromListOfFourMovies_thenReturnThreeRandomMovies() {
        //List of 4 movies
        when(movieServiceSpy.getAll()).thenReturn(ExemplarsCreator.createMovieDtoListWithFourMovies());

        List<MovieDto> randomThreeMovies = movieServiceSpy.getRandom();

        //List of 3 random movies
        assertEquals(3, randomThreeMovies.size());
        verify(movieServiceSpy).getAll();
    }

    @Test
    void whenGetRandomThreeMoviesFromListOfThreeMovies_thenReturnThreeRandomMovies() {
        //List of 3 movies
        when(movieServiceSpy.getAll()).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> randomThreeMovies = movieServiceSpy.getRandom();

        //List of 3 random movies
        assertEquals(3, randomThreeMovies.size());
        verify(movieServiceSpy).getAll();
    }

    @Test
    void whenGetRandomThreeMoviesFromEmptyList_thenReturnEmptyList() {
        //Empty list of movies
        when(movieServiceSpy.getAll()).thenReturn(List.of());

        List<MovieDto> randomThreeMovies = movieServiceSpy.getRandom();

        //Empty list of random movies
        assertTrue(randomThreeMovies.isEmpty());
        verify(movieServiceSpy).getAll();
    }

    @Test
    void whenGetByGenreId_thenReturnMovieListWithSameGenreId() {
        int genreId = 1;
        List<Movie> movieListWithThreeMoviesWithSameGenreId = ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId();

        when(movieRepository.findByGenresId(genreId)).thenReturn(movieListWithThreeMoviesWithSameGenreId);
        when(mapper.toMovieDtos(movieListWithThreeMoviesWithSameGenreId))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> moviesByGenreId = movieService.getByGenreId(genreId);

        assertEquals(3, moviesByGenreId.size());
        assertEquals("Movie 1", moviesByGenreId.get(0).getNameNative());
        assertEquals("Movie 2", moviesByGenreId.get(1).getNameNative());
        assertEquals("Movie 3", moviesByGenreId.get(2).getNameNative());
        verify(movieRepository).findByGenresId(genreId);
        verify(mapper).toMovieDtos(movieListWithThreeMoviesWithSameGenreId);
    }

    @Test
    void whenGetSortedMoviesWithNoParams_thenReturnDefaultAllMovies() {
        SortRequest request = SortRequest.builder()
                .sortType(null)
                .orderType(null)
                .build();

        when(movieServiceSpy.getAll())
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
        assertEquals(3, sortedMovies.size());
        assertEquals("Movie 1", sortedMovies.getFirst().getNameNative());
        assertEquals("Movie 2", sortedMovies.get(1).getNameNative());
        assertEquals("Movie 3", sortedMovies.get(2).getNameNative());
    }

    @Test
    void whenGetSortedMoviesWithSortedByNullAndOrderByASC_thenReturnDefaultAllMovies() {
        SortRequest request = SortRequest.builder()
                .sortType(null)
                .orderType(OrderType.ASC)
                .build();

        when(movieServiceSpy.getAll())
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
        assertEquals(3, sortedMovies.size());
        assertEquals("Movie 1", sortedMovies.getFirst().getNameNative());
        assertEquals("Movie 2", sortedMovies.get(1).getNameNative());
        assertEquals("Movie 3", sortedMovies.get(2).getNameNative());
    }

    @Test
    void whenGetSortedMoviesWithSortedByPriceAndOrderByNull_thenReturnSortedListByDefaultOrderASC() {
        SortRequest request = SortRequest.builder()
                .sortType(SortType.PRICE)
                .orderType(OrderType.ASC)
                .build();
        when(mapper.toMovieDtos(anyList())).thenReturn(ExemplarsCreator.createMovieDtoListWithFourMoviesSortedByPriceOrderAsc());

        List<MovieDto> sortedMovies = movieServiceSpy.getSortedMovies(request);
        assertEquals(4, sortedMovies.size());
        assertEquals(10, sortedMovies.getFirst().getPrice());
        assertEquals(12, sortedMovies.get(1).getPrice());
        assertEquals(15, sortedMovies.get(2).getPrice());
        assertEquals(20, sortedMovies.get(3).getPrice());
    }
}