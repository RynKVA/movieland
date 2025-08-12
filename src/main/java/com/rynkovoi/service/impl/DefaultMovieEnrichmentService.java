package com.rynkovoi.service.impl;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.common.dto.ReviewDto;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.MovieEnrichmentService;
import com.rynkovoi.service.ReleaseCountryService;
import com.rynkovoi.service.ReviewService;
import com.rynkovoi.type.EnrichmentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
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
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReleaseCountryService releaseCountryService;
    private final ReviewService reviewService;

    @Override
    public MovieResponse enrich(MovieResponse trimmedMovieResponse, EnrichmentType... types) {
        if (types == null) {
            return trimmedMovieResponse;
        }

        long movieId = trimmedMovieResponse.getId();
        List<EnrichmentType> listTypes = Arrays.asList(types);
        if (listTypes.contains(EnrichmentType.GENRES)) {
            log.info("Enriching movie with genres for movieId: {}", movieId);
            trimmedMovieResponse.setGenres(genreService.findByMovieId(movieId));
        }
        if (listTypes.contains(EnrichmentType.COUNTRIES)) {
            log.info("Enriching movie with release countries for movieId: {}", movieId);
            trimmedMovieResponse.setCountries(releaseCountryService.findByMovieId(movieId));
        }
        if (listTypes.contains(EnrichmentType.REVIEWS)) {
            log.info("Enriching movie with reviews for movieId: {}", movieId);
            trimmedMovieResponse.setReviews(reviewService.findByMovieId(movieId));
        }
        return trimmedMovieResponse;
    }

    @Override
    public MovieResponse asyncEnrich(MovieResponse trimmedMovieResponse, EnrichmentType... types) {
        if (types == null || types.length == 0) {
            nullifyFields(trimmedMovieResponse, EnumSet.allOf(EnrichmentType.class));
            return trimmedMovieResponse;
        }

        Set<EnrichmentType> enrichmentTypes = getNeededTypes(trimmedMovieResponse, EnumSet.copyOf(Arrays.asList(types)));

        long movieId = trimmedMovieResponse.getId();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        if (enrichmentTypes.contains(EnrichmentType.GENRES)) {
            CompletableFuture<List<GenreDto>> genreFuture = genreService.findAsyncByMovieId(movieId);
            CompletableFuture<Void> future = genreFuture
                    .orTimeout(5, SECONDS)
                    .thenAccept(trimmedMovieResponse::setGenres)
                    .exceptionally(e -> {
                        handleTaskException(genreFuture, EnrichmentType.GENRES, e, trimmedMovieResponse::setGenres);
                        return null;
                    });
            futures.add(future);
        }

        if (enrichmentTypes.contains(EnrichmentType.COUNTRIES)) {
            CompletableFuture<List<ReleaseCountryDto>> countriesFuture = releaseCountryService.findAsyncByMovieId(movieId);
            CompletableFuture<Void> future = countriesFuture
                    .orTimeout(5, TimeUnit.SECONDS)
                    .thenAccept(trimmedMovieResponse::setCountries)
                    .exceptionally(e -> {
                        handleTaskException(countriesFuture, EnrichmentType.COUNTRIES, e, trimmedMovieResponse::setCountries);
                        return null;
                    });
            futures.add(future);
        }

        if (enrichmentTypes.contains(EnrichmentType.REVIEWS)) {
            CompletableFuture<List<ReviewDto>> reviewsFuture = reviewService.findAsyncByMovieId(movieId);
            CompletableFuture<Void> future = reviewsFuture
                    .orTimeout(5, TimeUnit.SECONDS)
                    .thenAccept(trimmedMovieResponse::setReviews)
                    .exceptionally(e -> {
                        handleTaskException(reviewsFuture, EnrichmentType.REVIEWS, e, trimmedMovieResponse::setReviews);
                        return null;
                    });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return trimmedMovieResponse;
    }

    private Set<EnrichmentType> getNeededTypes(MovieResponse response, Set<EnrichmentType> enrichmentTypes) {
        Set<EnrichmentType> givenTypes = getAlreadyEnriched(response);

        Set<EnrichmentType> toEnrich = EnumSet.copyOf(enrichmentTypes);
        toEnrich.removeAll(givenTypes);

        givenTypes.removeAll(enrichmentTypes);

        if (!givenTypes.isEmpty()) {
            nullifyFields(response, givenTypes);
        }

        return toEnrich;
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

    private Set<EnrichmentType> getAlreadyEnriched(MovieResponse response) {
        Set<EnrichmentType> cachedEnrichmentTypes = new HashSet<>();
        if (response.getGenres() != null) {
            cachedEnrichmentTypes.add(GENRES);
        }
        if (response.getCountries() != null) {
            cachedEnrichmentTypes.add(COUNTRIES);
        }
        if (response.getReviews() != null) {
            cachedEnrichmentTypes.add(REVIEWS);
        }

        return cachedEnrichmentTypes;
    }

    private void nullifyFields(MovieResponse response, Set<EnrichmentType> types) {
        if (types == null || types.isEmpty()) {
            response.setGenres(null);
            response.setReviews(null);
            response.setCountries(null);
            return;
        }
        types.forEach(type -> {
            switch (type) {
                case GENRES -> response.setGenres(null);
                case REVIEWS -> response.setReviews(null);
                case COUNTRIES -> response.setCountries(null);
            }
        });
    }

}
