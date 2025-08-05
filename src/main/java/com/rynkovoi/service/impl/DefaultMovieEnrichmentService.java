package com.rynkovoi.service.impl;

import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.MovieEnrichmentService;
import com.rynkovoi.service.ReleaseCountryService;
import com.rynkovoi.service.ReviewService;
import com.rynkovoi.type.EnrichmentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReleaseCountryService releaseCountryService;
    private final ReviewService reviewService;

    @Override
    public MovieResponse enrich(MovieResponse trimmedMovieResponse, Set<EnrichmentType> types) {
        long movieId = trimmedMovieResponse.getId();
        if(types != null) {
            if (types.contains(EnrichmentType.GENRES)) {
                log.info("Enriching movie with genres for movieId: {}", movieId);
                trimmedMovieResponse.setGenres(genreService.findByMovieId(movieId));
            }
            if(types.contains(EnrichmentType.COUNTRIES)) {
                log.info("Enriching movie with release countries for movieId: {}", movieId);
                trimmedMovieResponse.setCountries(releaseCountryService.findByMovieId(movieId));
            }
            if(types.contains(EnrichmentType.REVIEWS)) {
                log.info("Enriching movie with reviews for movieId: {}", movieId);
                trimmedMovieResponse.setReviews(reviewService.findByMovieId(movieId));
            }
        }
        return trimmedMovieResponse;
    }
}
