package com.rynkovoi.service;

import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.type.EnrichmentType;

import java.util.Set;

public interface MovieEnrichmentService {

    MovieResponse enrich(MovieResponse trimmedMovieResponse, Set<EnrichmentType> types);

}
