package com.rynkovoi.web;

import com.github.database.rider.core.api.dataset.DataSet;
import com.rynkovoi.AbstractBaseITest;
import com.rynkovoi.properties.MovieProperties;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovieControllerTest extends AbstractBaseITest {

    @Autowired
    private MovieProperties movieProperties;

    private static final String All_MOVIES_RESPONSE_JSON_PATH = "response/all-movies-response.json";
    private static final String PAGE_MOVIES_RESPONSE_JSON_PATH = "response/page-movies-response.json";
    private static final String PAGE_MOVIES_SORTED_BY_PRICE_RESPONSE_JSON_PATH = "response/all-movies-sorted-by-price-response.json";
    private static final String PAGE_MOVIES_SORTED_BY_RATING_DESC_RESPONSE_JSON_PATH = "response/all-movies-sorted-by-rating-desc-response.json";
    private static final String TWO_MOVIES_RESPONSE = "response/random-two-movies-response.json";
    private static final String GET_MOVIES_BY_GENRE_ID = "response/get-movies-by-genre-id-response.json";
    private static final String GET_MOVIES_BY_ID = "response/get-by-id.json";
    private static final String GET_MOVIES_BY_ID_AND_CURRENCY_CODE_USD = "response/get-by-id-with-currency-code-usd.json";

    @Test
    @DataSet(value = "datasets/three-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenGetAllNoParamsProvided_thenReturnAllMoviesWithoutSorting() throws Exception {
        SQLStatementCountValidator.reset();
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(PAGE_MOVIES_RESPONSE_JSON_PATH)));

        SQLStatementCountValidator.assertSelectCount(2);
    }

    @Test
    @DataSet(value = "datasets/three-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenGetAllWithSortingByPriceAndOrderByASC_thenReturnAllMoviesSortedByPrice() throws Exception {
        SQLStatementCountValidator.reset();
        mockMvc.perform(get("/api/v1/movies")
                        .param("sortType", "PRICE")
                        .param("direction", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(PAGE_MOVIES_SORTED_BY_PRICE_RESPONSE_JSON_PATH), true));
        SQLStatementCountValidator.assertSelectCount(2);
    }

    @Test
    @DataSet(value = "datasets/three-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenGetAllWithSortingByRatingOrderByDESC_thenReturnAllMoviesSortedByRatingOrderByDESC() throws Exception {
        SQLStatementCountValidator.reset();
        mockMvc.perform(get("/api/v1/movies")
                        .param("sortType", "RATING")
                        .param("direction", "DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(PAGE_MOVIES_SORTED_BY_RATING_DESC_RESPONSE_JSON_PATH), true));
        SQLStatementCountValidator.assertSelectCount(2);
    }

    @Test
    @DataSet(value = "datasets/two-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetRandomMoviesWithLessThanIndicatedInProperty_thenReturnAllMovies() throws Exception {
        SQLStatementCountValidator.reset();
        int countOfMovies = 2;

        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(TWO_MOVIES_RESPONSE)))
                .andExpect(jsonPath("$.length()").value(countOfMovies));

        assertTrue(movieProperties.getRandomCount() > countOfMovies);
        SQLStatementCountValidator.assertSelectCount(1);
    }

    @Test
    @DataSet(value = "datasets/three-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetRandomMoviesWithSameMoviesAsIndicatedInProperty_thenReturnAllMovies() throws Exception {
        SQLStatementCountValidator.reset();
        int countOfMovies = 3;
        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(All_MOVIES_RESPONSE_JSON_PATH)))
                .andExpect(jsonPath("$.length()").value(countOfMovies));

        assertEquals(movieProperties.getRandomCount(), countOfMovies);
        SQLStatementCountValidator.assertSelectCount(1);
    }

    @Test
    @DataSet(value = "datasets/four-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetRandomMoviesWithMoreMoviesThanIndicatedInProperty_thenReturnIndicatedCountOfRandomMovies() throws Exception {
        SQLStatementCountValidator.reset();
        int countOfMovies = 4;
        int expectedCountOfRandomMovies = movieProperties.getRandomCount();

        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedCountOfRandomMovies))
                .andExpect(jsonPath("$[*].id", everyItem(oneOf(1, 2, 3, 4))));


        assertTrue(expectedCountOfRandomMovies < countOfMovies);

        SQLStatementCountValidator.assertSelectCount(1);
    }

    @Test
    @DataSet(value = "datasets/four-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetMoviesByGenre_thenReturnCorrespondingMovies() throws Exception {
        SQLStatementCountValidator.reset();
        int genreId = 1;

        mockMvc.perform(get("/api/v1/movies/genres/{genreId}", genreId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(GET_MOVIES_BY_GENRE_ID)))
                .andExpect(jsonPath("$[*].id", everyItem(oneOf(1, 4))));

        SQLStatementCountValidator.assertSelectCount(1);
    }

    @Test
    @DataSet(value = "datasets/four-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetMoviesByGenreWithNotExistingGenreId_thenReturnEmptyList() throws Exception {
        SQLStatementCountValidator.reset();
        int genreId = 10;

        mockMvc.perform(get("/api/v1/movies/genres/{genreId}", genreId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        SQLStatementCountValidator.assertSelectCount(1);
    }

    @Test
    @DataSet(value = "datasets/four-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetById_thenReturnMovieResponse() throws Exception {

        SQLStatementCountValidator.reset();
        long movieId = 1;

        mockMvc.perform(get("/api/v1/movies/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(GET_MOVIES_BY_ID)));

        SQLStatementCountValidator.assertSelectCount(1);
    }

    @Test
    @DataSet(value = "datasets/four-movies.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void whenTestGetByIdAndCurrencyCodeUSD_thenReturnMovieResponseWithPriceUSD() throws Exception {

        SQLStatementCountValidator.reset();
        long movieId = 1;

        mockMvc.perform(get("/api/v1/movies/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currency", "USD"))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(GET_MOVIES_BY_ID_AND_CURRENCY_CODE_USD)));

        SQLStatementCountValidator.assertSelectCount(1);
    }

}