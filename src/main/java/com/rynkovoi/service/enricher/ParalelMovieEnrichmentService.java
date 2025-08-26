package com.rynkovoi.service.enricher;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.common.dto.ReviewDto;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.properties.EnrichProperty;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.ReleaseCountryService;
import com.rynkovoi.service.ReviewService;
import com.rynkovoi.type.EnrichmentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.rynkovoi.type.EnrichmentType.COUNTRIES;
import static com.rynkovoi.type.EnrichmentType.GENRES;
import static com.rynkovoi.type.EnrichmentType.REVIEWS;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("parallel-enrichment")
public class ParalelMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReleaseCountryService releaseCountryService;
    private final ReviewService reviewService;
    private final EnrichProperty enrichProperty;
    private final ExecutorService enrichmentExecutorService;

    @Override
    public void enrich(MovieResponse trimmedMovieResponse, EnrichmentType... types) {
        log.info("Enriching movie response with types: {}", Arrays.toString(types));

        if (types == null || types.length == 0) {
            return;
        }
        try {
            enrichmentExecutorService.invokeAll(createEnrichmentTasks(trimmedMovieResponse, types),
                    enrichProperty.getTimeout(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.warn("Enrichment tasks were interrupted", e);
        }
    }

    private List<Callable<Object>> createEnrichmentTasks(MovieResponse trimmedMovieResponse, EnrichmentType... types){
        List<Callable<Object>> tasks = new ArrayList<>();
        long movieId = trimmedMovieResponse.getId();

        if (Arrays.asList(types).contains(GENRES)) {
            Callable<Object> genreTask = Executors.callable(() -> {
                List<GenreDto> genres = genreService.findByMovieId(movieId);
                trimmedMovieResponse.setGenres(genres);
            });
            tasks.add(genreTask);
        }
        if (Arrays.asList(types).contains(COUNTRIES)) {
            Callable<Object> countriesTask = Executors.callable(() -> {
                List<ReleaseCountryDto> countries = releaseCountryService.findByMovieId(movieId);
                trimmedMovieResponse.setCountries(countries);
            });
            tasks.add(countriesTask);
        }
        if (Arrays.asList(types).contains(REVIEWS)) {
            Callable<Object> reviewsTask = Executors.callable(() -> {
                List<ReviewDto> reviews = reviewService.findByMovieId(movieId);
                trimmedMovieResponse.setReviews(reviews);
            });
            tasks.add(reviewsTask);
        }
        return tasks;
    }
}


