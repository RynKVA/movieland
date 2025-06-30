//package com.rynkovoi.service.impl;
//
//import com.rynkovoi.ExemplarsCreator;
//import com.rynkovoi.mapper.GenresMapper;
//import com.rynkovoi.model.Genre;
//import com.rynkovoi.service.cache.GenreCache;
//import com.rynkovoi.web.dto.GenreDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class DefaultGenreServiceTest {
//
//    @Mock
//    private GenresMapper genresMapper;
//
//    @Mock
//    private GenreCache<Genre> cache;
//
//    @InjectMocks
//    private DefaultGenresService genreService;
//
//
//    @Test
//    void whenGetAllGenres_thenReturnAllGenresFromGenresCache() {
//        List<Genre> genreListWithThreeGenres = ExemplarsCreator.createGenreListWithThreeGenres();
//        when(cache.getValues())
//                .thenReturn(genreListWithThreeGenres);
//        when(genresMapper.toGenreDto(genreListWithThreeGenres)).thenReturn(ExemplarsCreator.createGenreDtoListWithThreeGenres());
//
//        List<GenreDto> allGenres = genreService.getAllGenres();
//        assertEquals(3, allGenres.size());
//        assertEquals("Action", allGenres.get(0).getName());
//        assertEquals("Comedy", allGenres.get(1).getName());
//        assertEquals("Drama", allGenres.get(2).getName());
//
//        verify(cache).getValues();
//        verify(genresMapper).toGenreDto(genreListWithThreeGenres);
//    }
//}