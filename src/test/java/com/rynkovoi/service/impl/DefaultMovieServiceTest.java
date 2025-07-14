package com.rynkovoi.service.impl;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.exception.MovieNotFoundException;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.properties.MovieProperties;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.converter.CurrencyConverter;
import com.rynkovoi.type.CurrencyCode;
import com.rynkovoi.type.SortDirection;
import com.rynkovoi.type.SortType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private CurrencyConverter currencyConverter;

    @InjectMocks
    private DefaultMovieService movieService;

    private DefaultMovieService movieServiceSpy;

    private MovieFilter movieFilter;

    @BeforeEach
    void setUp() {
        MovieProperties movieProperties = new MovieProperties();
        movieProperties.setRandomCount(3);
        movieServiceSpy = spy(new DefaultMovieService(movieRepository, currencyConverter, mapper, movieProperties));

        movieFilter = MovieFilter.builder().build();
    }

    @Test
    void whenUseGetAllMovies_thenReturnAll() {
        List<Movie> movies = List.of();
        when(movieRepository.findAll(Sort.unsorted())).thenReturn(movies);
        when(mapper.toMovieDtos(movies)).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> allMovies = movieService.getAll(movieFilter);
        assertEquals(3, allMovies.size());
        assertEquals("Movie 1", allMovies.get(0).getNameNative());
        assertEquals("Movie 2", allMovies.get(1).getNameNative());
        assertEquals("Movie 3", allMovies.get(2).getNameNative());

        verify(movieRepository).findAll(Sort.unsorted());
        verify(mapper).toMovieDtos(movies);
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
        MovieFilter request = MovieFilter.builder()
                .sortType(null)
                .sortDirection(null)
                .build();

        when(movieRepository.findAll(any()))
                .thenReturn(ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId());
        when(mapper.toMovieDtos(anyList()))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> sortedMovies = movieServiceSpy.getAll(request);
        assertEquals(3, sortedMovies.size());
        assertEquals("Movie 1", sortedMovies.getFirst().getNameNative());
        assertEquals("Movie 2", sortedMovies.get(1).getNameNative());
        assertEquals("Movie 3", sortedMovies.get(2).getNameNative());
    }

    @Test
    void whenGetSortedMoviesWithSortedByNullAndOrderByASC_thenReturnDefaultAllMovies() {
        MovieFilter request = MovieFilter.builder()
                .sortType(null)
                .sortDirection(SortDirection.ASC)
                .build();

        when(movieRepository.findAll(any()))
                .thenReturn(ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId());
        when(mapper.toMovieDtos(anyList()))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        List<MovieDto> sortedMovies = movieServiceSpy.getAll(request);
        assertEquals(3, sortedMovies.size());
        assertEquals("Movie 1", sortedMovies.getFirst().getNameNative());
        assertEquals("Movie 2", sortedMovies.get(1).getNameNative());
        assertEquals("Movie 3", sortedMovies.get(2).getNameNative());
    }

    @Test
    void whenGetSortedMoviesWithSortedByPriceAndOrderByNull_thenReturnSortedListByDefaultOrderASC() {
        MovieFilter request = MovieFilter.builder()
                .sortType(SortType.PRICE)
                .sortDirection(SortDirection.ASC)
                .build();
        when(mapper.toMovieDtos(anyList())).thenReturn(ExemplarsCreator.createMovieDtoListWithFourMoviesSortedByPriceOrderAsc());

        List<MovieDto> sortedMovies = movieServiceSpy.getAll(request);
        assertEquals(4, sortedMovies.size());
        assertEquals(10, sortedMovies.getFirst().getPrice());
        assertEquals(12, sortedMovies.get(1).getPrice());
        assertEquals(15, sortedMovies.get(2).getPrice());
        assertEquals(20, sortedMovies.get(3).getPrice());
    }

    @Test
    void whenGeTByIdWithExistingId_thenReturnCorrespondingMovieResponse() {
        Long movieId = 1L;
        BigDecimal price = BigDecimal.valueOf(10.99);
        Movie movie = ExemplarsCreator.createMovieBuilder().id(movieId)
                .nameNative("Movie 1")
                .price(price)
                .build();
        MovieResponse movieResponse = ExemplarsCreator.createMovieResponseBuilder().build();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(currencyConverter.convert(movie.getPrice(), CurrencyCode.USD)).thenReturn(price);
        when(mapper.toMovieResponse(movie, price)).thenReturn(movieResponse);

        MovieResponse response = movieService.getById(movieId, CurrencyCode.USD);
        assertEquals(movieId, response.getId());
        assertEquals("Movie 1", response.getNameNative());

        verify(movieRepository).findById(movieId);
        verify(mapper).toMovieResponse(movie, price);
    }

    @Test
    void whenGeTByIdWithNotExistingId_thenExpectMovieNotFoundException() {
        long movieId = 100L;

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class,
                () -> movieService.getById(movieId, CurrencyCode.USD));
        assertEquals("Movie not found with id: 100", exception.getMessage());
    }
}