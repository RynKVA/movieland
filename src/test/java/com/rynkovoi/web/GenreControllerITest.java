package com.rynkovoi.web;

import com.github.database.rider.core.api.dataset.DataSet;
import com.rynkovoi.AbstractBaseITest;
import com.rynkovoi.repository.impl.CachedGenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.lang.reflect.Method;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GenreControllerITest extends AbstractBaseITest {

    @Autowired
    private CachedGenreRepository genreRepository;

    private static final String RESPONSE_JSON_PATH = "response/all-genre-response.json";

    @BeforeEach
    void setUp() throws Exception {
        Method method = CachedGenreRepository.class.getDeclaredMethod("updateCache");
        method.setAccessible(true);
        method.invoke(genreRepository);
    }

    @Test
    @DataSet(value = "datasets/genres.yml", cleanBefore = true, cleanAfter = true, skipCleaningFor = "flyway_schema_history")
    void testGetAllGenres() throws Exception {

        mockMvc.perform(get("/api/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseAsString(RESPONSE_JSON_PATH)));
    }
}