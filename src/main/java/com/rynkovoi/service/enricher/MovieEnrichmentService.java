package com.rynkovoi.service.enricher;

import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.type.EnrichmentType;

public interface MovieEnrichmentService {

    void enrich(MovieResponse trimmedMovieResponse, EnrichmentType ... types);
}
