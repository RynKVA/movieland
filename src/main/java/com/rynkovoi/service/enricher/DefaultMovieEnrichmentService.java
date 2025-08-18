package com.rynkovoi.service.enricher;

import com.rynkovoi.common.response.MovieResponse;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("default-enrichment")
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReleaseCountryService releaseCountryService;
    private final ReviewService reviewService;

    @Override
    public void enrich(MovieResponse trimmedMovieResponse, EnrichmentType... types) {
        if (types == null) {
            return;
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
    }
}
