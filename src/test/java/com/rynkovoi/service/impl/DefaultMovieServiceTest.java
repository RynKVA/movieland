package com.rynkovoi.service.impl;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.common.MovieFilter;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.PageWrapper;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.exception.MovieNotFoundException;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.properties.MovieProperties;
import com.rynkovoi.repository.CriteriaMovieRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultMovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CriteriaMovieRepository criteriaMovieRepository;

    @Mock
    private CommonMapper mapper;

    @Mock
    private CurrencyConverter currencyConverter;

    @InjectMocks
    private DefaultMovieService movieService;

    private MovieFilter movieFilter;

    @BeforeEach
    void setUp() {
        MovieProperties movieProperties = new MovieProperties();
        movieProperties.setRandomCount(3);
        DefaultMovieService movieServiceSpy = spy(new DefaultMovieService(criteriaMovieRepository, movieRepository, currencyConverter, mapper, movieProperties));

        movieFilter = MovieFilter.builder().build();
    }

    @Test
    void whenUseGetAllMovies_thenReturnPageWrapper() {
        when(criteriaMovieRepository.findAll(movieFilter))
                .thenReturn(new PageImpl<>(ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId(), PageRequest.of(0, 5), 3));
        when(mapper.toMovieDtos(any())).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        PageWrapper<MovieDto> pageWrapper = movieService.getAll(movieFilter);

        List<MovieDto> content = pageWrapper.getContent();
        assertEquals(3, content.size());
        assertEquals("Movie 1", content.get(0).getNameNative());
        assertEquals("Movie 2", content.get(1).getNameNative());
        assertEquals("Movie 3", content.get(2).getNameNative());
        assertEquals(3, pageWrapper.getTotalElements());
        assertEquals(1, pageWrapper.getTotalPages());
        assertEquals(0, pageWrapper.getCurrentPage());

        verify(criteriaMovieRepository).findAll(movieFilter);
        verify(mapper).toMovieDtos(any());
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

        when(criteriaMovieRepository.findAll(request))
                .thenReturn(new PageImpl<>(ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId(), PageRequest.of(0, 5), 3));
        when(mapper.toMovieDtos(any())).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        PageWrapper<MovieDto> pageWrapper = movieService.getAll(request);

        List<MovieDto> content = pageWrapper.getContent();
        assertEquals(3, content.size());
        assertEquals("Movie 1", content.get(0).getNameNative());
        assertEquals("Movie 2", content.get(1).getNameNative());
        assertEquals("Movie 3", content.get(2).getNameNative());
        assertEquals(3, pageWrapper.getTotalElements());
        assertEquals(1, pageWrapper.getTotalPages());
        assertEquals(0, pageWrapper.getCurrentPage());

        verify(criteriaMovieRepository).findAll(request);
        verify(mapper).toMovieDtos(any());
    }

    @Test
    void whenGetSortedMoviesWithSortedByNullAndOrderByASC_thenReturnDefaultAllMovies() {
        MovieFilter request = MovieFilter.builder()
                .sortType(null)
                .sortDirection(SortDirection.ASC)
                .build();

        when(criteriaMovieRepository.findAll(request))
                .thenReturn(new PageImpl<>(ExemplarsCreator.createMovieListWithThreeMoviesWithSameGenreId(), PageRequest.of(0, 5), 3));
        when(mapper.toMovieDtos(any())).thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        PageWrapper<MovieDto> pageWrapper = movieService.getAll(request);

        List<MovieDto> content = pageWrapper.getContent();
        assertEquals(3, content.size());
        assertEquals("Movie 1", content.get(0).getNameNative());
        assertEquals("Movie 2", content.get(1).getNameNative());
        assertEquals("Movie 3", content.get(2).getNameNative());
        assertEquals(3, pageWrapper.getTotalElements());
        assertEquals(1, pageWrapper.getTotalPages());
        assertEquals(0, pageWrapper.getCurrentPage());

        verify(criteriaMovieRepository).findAll(request);
        verify(mapper).toMovieDtos(any());
    }

    @Test
    void whenGetSortedMoviesWithSortedByPriceAndOrderByNull_thenReturnSortedListByDefaultOrderASC() {
        MovieFilter request = MovieFilter.builder()
                .sortType(SortType.PRICE)
                .sortDirection(SortDirection.ASC)
                .build();
        when(criteriaMovieRepository.findAll(request))
                .thenReturn(new PageImpl<>(ExemplarsCreator.createMovieListWithFourMoviesSortedByPriceOrderAsc(), PageRequest.of(0, 5), 4));
        when(mapper.toMovieDtos(any())).thenReturn(ExemplarsCreator.createMovieDtoListWithFourMoviesSortedByPriceOrderAsc());

        PageWrapper<MovieDto> pageWrapper = movieService.getAll(request);

        List<MovieDto> content = pageWrapper.getContent();
        assertEquals(4, content.size());
        assertEquals(10, content.getFirst().getPrice());
        assertEquals(12, content.get(1).getPrice());
        assertEquals(15, content.get(2).getPrice());
        assertEquals(20, content.get(3).getPrice());
        assertEquals(4, pageWrapper.getTotalElements());
        assertEquals(1, pageWrapper.getTotalPages());
        assertEquals(0, pageWrapper.getCurrentPage());

        verify(criteriaMovieRepository).findAll(request);
        verify(mapper).toMovieDtos(any());

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

        when(movieRepository.findByIdWithNestedData(movieId)).thenReturn(Optional.of(movie));
        when(currencyConverter.convert(movie.getPrice(), CurrencyCode.USD)).thenReturn(price);
        when(mapper.toMovieResponse(movie, price)).thenReturn(movieResponse);

        MovieResponse response = movieService.getById(movieId, CurrencyCode.USD);
        assertEquals(movieId, response.getId());
        assertEquals("Movie 1", response.getNameNative());

        verify(movieRepository).findByIdWithNestedData(movieId);
        verify(currencyConverter).convert(movie.getPrice(), CurrencyCode.USD);
        verify(mapper).toMovieResponse(movie, price);
    }

    @Test
    void whenGeTByIdWithExistingIdWithDefaultCurrencyCode_thenReturnCorrespondingMovieResponse() {
        Long movieId = 1L;
        BigDecimal price = BigDecimal.valueOf(10.99);
        Movie movie = ExemplarsCreator.createMovieBuilder().id(movieId)
                .nameNative("Movie 1")
                .price(price)
                .build();
        MovieResponse movieResponse = ExemplarsCreator.createMovieResponseBuilder().build();

        when(movieRepository.findByIdWithNestedData(movieId)).thenReturn(Optional.of(movie));
        when(mapper.toMovieResponse(movie, price)).thenReturn(movieResponse);

        MovieResponse response = movieService.getById(movieId, CurrencyCode.UAH);
        assertEquals(movieId, response.getId());
        assertEquals("Movie 1", response.getNameNative());

        verify(movieRepository).findByIdWithNestedData(movieId);
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