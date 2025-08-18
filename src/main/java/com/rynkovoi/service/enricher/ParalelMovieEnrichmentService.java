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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

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

    @Override
    public void enrich(MovieResponse trimmedMovieResponse, EnrichmentType... types) {
        log.info("Enriching movie response with types: {}", Arrays.toString(types));

        if (types == null || types.length == 0) {
            return;
        }

        long movieId = trimmedMovieResponse.getId();
        ExecutorService executor = Executors.newCachedThreadPool();

        if (Arrays.asList(types).contains(GENRES)) {
            Future<List<GenreDto>> genreFuture = executor.submit(() -> genreService.findByMovieId(movieId));
            handleFuture(genreFuture, trimmedMovieResponse::setGenres, GENRES.name());
        }
        if (Arrays.asList(types).contains(COUNTRIES)) {
            Future<List<ReleaseCountryDto>> countryFuture = executor.submit(() -> releaseCountryService.findByMovieId(movieId));
            handleFuture(countryFuture, trimmedMovieResponse::setCountries, COUNTRIES.name());
        }
        if (Arrays.asList(types).contains(REVIEWS)) {
            Future<List<ReviewDto>> reviewFuture = executor.submit(() -> reviewService.findByMovieId(movieId));
            handleFuture(reviewFuture, trimmedMovieResponse::setReviews, REVIEWS.name());
        }
    }

    private <T> void handleFuture(Future<T> future, Consumer<T> setter, String name) {
        try {
            setter.accept(future.get(enrichProperty.getTimeout(), TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            log.warn("{} enrichment timed out", name);
            future.cancel(true);
        } catch (Exception e) {
            log.error("{} enrichment failed", name, e);
            future.cancel(true);
        }
    }
}


