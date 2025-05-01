package com.rynkovoi.web;

import com.rynkovoi.ExemplarsCreator;
import com.rynkovoi.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void testGetSortedOrDefaultMovies_WhenParamsProvided() throws Exception {
        String sortBy = "price";
        String order = "asc";

        when(movieService.getSortedMovies(sortBy, order))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        mockMvc.perform(get("/api/v1/movies")
                        .param("sortBy", sortBy)
                        .param("order", order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieService).getSortedMovies(sortBy, order);
    }

    @Test
    void testGetSortedOrDefaultMovies_WhenNoParamsProvided() throws Exception {
        when(movieService.getSortedMovies(null, null))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieService).getSortedMovies(null, null);
    }

    @Test
    void testGetRandomThreeMovies() throws Exception {
        when(movieService.getRandomThreeMovies())
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieService).getRandomThreeMovies();
    }

    @Test
    void testGetMoviesByGenre() throws Exception {
        int genreId = 1;
        when(movieService.getMoviesByGenreId(genreId))
                .thenReturn(ExemplarsCreator.createMovieDtoListWithThreeMovies());

        mockMvc.perform(get("/api/v1/movies/genre/{genreId}", genreId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieService).getMoviesByGenreId(genreId);
    }
}