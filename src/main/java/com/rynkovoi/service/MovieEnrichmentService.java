package com.rynkovoi.service;

import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.type.EnrichmentType;

public interface MovieEnrichmentService {

    MovieResponse enrich(MovieResponse trimmedMovieResponse, EnrichmentType ... types);

    MovieResponse asyncEnrich(MovieResponse trimmedMovieResponse, EnrichmentType... types);
}
