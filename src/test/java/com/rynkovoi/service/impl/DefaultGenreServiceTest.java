package com.rynkovoi.service.impl;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.exception.GenreNotFoundException;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.cache.GenresCacheService;
import com.rynkovoi.web.dto.GenreDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DefaultGenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenresCacheService genresCacheService;

    @InjectMocks
    private DefaultGenreService genreService;

    @Test
    void testSaveGenres() {
        List<GenreDto> genres = ExemplarsCreator.createGenreDtoListWithThreeGenres();
        doNothing().when(genreRepository).saveGenres(genres);

        genreService.saveGenres(genres);

        verify(genreRepository).saveGenres(anyList());
    }

    @Test
    void whenGetAllGenres_thenReturnAllGenresFromGenresCache() {
        when(genresCacheService.getAllGenres())
                .thenReturn(ExemplarsCreator.createGenreDtoListWithThreeGenres());

        List<GenreDto> allGenres = genreService.getAllGenres();
        assertEquals(3, allGenres.size());
        assertEquals("Action", allGenres.get(0).getName());
        assertEquals("Comedy", allGenres.get(1).getName());
        assertEquals("Drama", allGenres.get(2).getName());

        verify(genresCacheService).getAllGenres();
    }

    @Test
    void whenGetGenreWithExistingId_thenReturnCorrespondingGenre() {
        int genreId = 1;
        when(genresCacheService.getById(genreId)).thenReturn(
                ExemplarsCreator.createGenreDtoBuilder().build()
        );

        GenreDto genre = genreService.getGenre(genreId);

        assertEquals(1, genre.getId());
        assertEquals("Action", genre.getName());

        verify(genresCacheService).getById(genreId);
    }

    @Test
    void whenGetGenreWithNotExistingId_thenExpectGenreNotFoundException() {
        int notExistingGenreId = 1;
        when(genresCacheService.getById(notExistingGenreId)).thenThrow(
                new GenreNotFoundException("Genre not found with id: %s".formatted(notExistingGenreId))
        );

        GenreNotFoundException exception = assertThrows(GenreNotFoundException.class,
                () -> genreService.getGenre(notExistingGenreId));

        assertEquals("Genre not found with id: 1", exception.getMessage());
        verify(genresCacheService).getById(notExistingGenreId);
    }
}