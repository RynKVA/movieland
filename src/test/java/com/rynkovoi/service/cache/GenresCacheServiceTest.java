package com.rynkovoi.service.cache;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.exception.GenreNotFoundException;
import com.rynkovoi.mapper.GenresMapper;
import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.web.dto.GenreDto;
import generated.tables.records.GenresRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GenresCacheServiceTest {

    @Mock
    private GenresMapper genresMapper;
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreCache cache;

    @InjectMocks
    private GenresCacheService genresCacheService;

    @Test
    void whenGetAllGenres_themReturnAllGenresFromCache() {
        List<GenresRecord> genreRecordListWithThreeGenres = ExemplarsCreator.createGenreRecordListWithThreeGenres();

        when(cache.getValues())
                .thenReturn(genreRecordListWithThreeGenres);
        when(genresMapper.toGenreDto(genreRecordListWithThreeGenres))
                .thenReturn(ExemplarsCreator.createGenreDtoListWithThreeGenres());

        List<GenreDto> allGenres = genresCacheService.getAllGenres();

        assertEquals(3, allGenres.size());
        assertEquals("Action", allGenres.get(0).getName());
        assertEquals("Comedy", allGenres.get(1).getName());
        assertEquals("Drama", allGenres.get(2).getName());
        verify(cache).getValues();
        verify(genresMapper).toGenreDto(genreRecordListWithThreeGenres);
    }

    @Test
    void whenGetByExistingId_thenReturnCorrespondingGenre() {
        int genreId = 1;

        when(cache.getValues())
                .thenReturn(ExemplarsCreator.createGenreRecordListWithThreeGenres());
        when(genresMapper.toGenreDto(any(GenresRecord.class)))
                .thenReturn(ExemplarsCreator.createGenreDtoBuilder().id(genreId).build());

        GenreDto genre = genresCacheService.getById(genreId);

        assertEquals(genreId, genre.getId());
        assertEquals("Action", genre.getName());
        verify(cache).getValues();
        verify(genresMapper).toGenreDto(any(GenresRecord.class));
    }

    @Test
    void whenGetByNotExistingId_thenExpectGenreNotFoundException() {
        int genreId = 10;

        when(cache.getValues())
                .thenReturn(ExemplarsCreator.createGenreRecordListWithThreeGenres());

        GenreNotFoundException exception = assertThrows(GenreNotFoundException.class,
                () -> genresCacheService.getById(genreId));

        assertEquals("Genre not found with id: 10", exception.getMessage());
        verify(cache).getValues();
    }

    @Test
    void whenGetGenreIdByExistingName_thenReturnCorrespondingGenre() {
        String nameGenre = "Action";

        when(cache.getValues())
                .thenReturn(ExemplarsCreator.createGenreRecordListWithThreeGenres());

        Integer genreIdByName = genresCacheService.getGenreIdByName(nameGenre);

        assertEquals(1, genreIdByName);
        verify(cache).getValues();
    }

    @Test
    void whenGetGenreIdByNotExistingName_thenExpectGenreNotFoundException() {
        String nameGenre = "Melodrama";

        when(cache.getValues())
                .thenReturn(ExemplarsCreator.createGenreRecordListWithThreeGenres());

        GenreNotFoundException exception = assertThrows(GenreNotFoundException.class,
                () -> genresCacheService.getGenreIdByName(nameGenre));

        assertEquals("Genre not found with name: Melodrama", exception.getMessage());
        verify(cache).getValues();
    }

    @Test
    void whenValidateCacheOnEmptyCache_thenCacheUpdateFromDB() {
        GenresCacheService genresCacheServiceSpy = spy(new GenresCacheService(genresMapper, genreRepository, cache));
        when(cache.isEmpty())
                .thenReturn(true);
        doNothing()
                .when(genresCacheServiceSpy)
                .updateCache();

        genresCacheServiceSpy.validateCache();

        verify(genresCacheServiceSpy).updateCache();
        verify(cache).isEmpty();
    }

    @Test
    void whenValidateCacheOnNotEmptyCache_thenCacheNotUpdateFromDB() {
        GenresCacheService genresCacheServiceSpy = spy(new GenresCacheService(genresMapper, genreRepository, cache));
        when(cache.getValues())
                .thenReturn(ExemplarsCreator.createGenreRecordListWithThreeGenres());

        genresCacheServiceSpy.validateCache();

        verify(cache).isEmpty();
        verify(genresCacheServiceSpy, never()).updateCache();
    }
}