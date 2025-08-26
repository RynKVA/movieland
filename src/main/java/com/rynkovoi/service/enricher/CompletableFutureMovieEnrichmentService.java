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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static com.rynkovoi.type.EnrichmentType.COUNTRIES;
import static com.rynkovoi.type.EnrichmentType.GENRES;
import static com.rynkovoi.type.EnrichmentType.REVIEWS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("completable-future-enrichment")
public class CompletableFutureMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReleaseCountryService releaseCountryService;
    private final ReviewService reviewService;
    private final EnrichProperty enrichProperty;
    private final ExecutorService enrichmentExecutorService;

    @Override
    public void enrich(MovieResponse trimmedMovieResponse, EnrichmentType... types) {
        if (types == null || types.length == 0) {
            return;
        }

        long movieId = trimmedMovieResponse.getId();
        int timeout = enrichProperty.getTimeout();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        List<EnrichmentType> listEnrichType = Arrays.asList(types);
        if (listEnrichType.contains(GENRES)) {
            CompletableFuture<List<GenreDto>> genreFuture = CompletableFuture.supplyAsync(
                    () -> genreService.findByMovieId(movieId), enrichmentExecutorService);
            CompletableFuture<Void> future = genreFuture
                    .orTimeout(timeout, SECONDS)
                    .thenAccept(trimmedMovieResponse::setGenres)
                    .exceptionally(e -> {
                        handleTaskException(genreFuture, EnrichmentType.GENRES, e, trimmedMovieResponse::setGenres);
                        return null;
                    });
            futures.add(future);
        }

        if (listEnrichType.contains(COUNTRIES)) {
            CompletableFuture<List<ReleaseCountryDto>> countriesFuture = CompletableFuture.supplyAsync(
                    () -> releaseCountryService.findByMovieId(movieId), enrichmentExecutorService);
            CompletableFuture<Void> future = countriesFuture
                    .orTimeout(timeout, TimeUnit.SECONDS)
                    .thenAccept(trimmedMovieResponse::setCountries)
                    .exceptionally(e -> {
                        handleTaskException(countriesFuture, EnrichmentType.COUNTRIES, e, trimmedMovieResponse::setCountries);
                        return null;
                    });
            futures.add(future);
        }

        if (listEnrichType.contains(REVIEWS)) {
            CompletableFuture<List<ReviewDto>> reviewsFuture = CompletableFuture.supplyAsync(
                    () -> reviewService.findByMovieId(movieId), enrichmentExecutorService);
            CompletableFuture<Void> future = reviewsFuture
                    .orTimeout(timeout, TimeUnit.SECONDS)
                    .thenAccept(trimmedMovieResponse::setReviews)
                    .exceptionally(e -> {
                        handleTaskException(reviewsFuture, EnrichmentType.REVIEWS, e, trimmedMovieResponse::setReviews);
                        return null;
                    });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private <T> void handleTaskException(CompletableFuture<List<T>> future, EnrichmentType type,
                                         Throwable e, Consumer<List<T>> consumer) {
        Throwable cause = e instanceof CompletionException ? e.getCause() : e;

        if (cause instanceof TimeoutException) {
            log.warn("{} enrichment timed out", type.name());
            future.cancel(true);
            consumer.accept(null);
        } else {
            log.warn("{} enrichment failed: {}", type.name(), cause.getMessage());
        }
    }
}
