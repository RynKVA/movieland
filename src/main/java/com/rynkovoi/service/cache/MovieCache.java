package com.rynkovoi.service.cache;

import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.exception.MovieNotFoundException;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.Movie;
import com.rynkovoi.repository.MovieRepository;
import com.rynkovoi.service.enricher.MovieEnrichmentService;
import com.rynkovoi.type.EnrichmentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@Slf4j
@Service
@ManagedResource(objectName = "com.rynkovoi:type=MovieCache", description = "Cache for movies")
public class MovieCache {

    private final Map<Long, SoftReference<MovieResponse>> movieCache = new ConcurrentHashMap<>();

    private final BiFunction<Long,SoftReference<MovieResponse>, SoftReference<MovieResponse>> function;

    private final CommonMapper mapper;

    public MovieCache(MovieRepository movieRepository, CommonMapper mapper, MovieEnrichmentService enrichmentService) {
        this.mapper = mapper;
        this.function = (id, reference) -> {
            if (reference != null && reference.get() != null) {
                log.info("Movie with id {} is already in cache", id);
                return reference;
            }
            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new MovieNotFoundException(id));
            MovieResponse movieResponse = mapper.toTrimmedMovieResponse(movie);
            enrichmentService.enrich(movieResponse, EnrichmentType.values());
            return new SoftReference<>(mapper.copy(movieResponse));
        };
    }

    public MovieResponse get(long movieId) {
        SoftReference<MovieResponse> movieResponseSoftReference = movieCache.compute(movieId, function);
        return mapper.copy(movieResponseSoftReference.get());
    }

    public void update(long movieId, MovieResponse movieResponse) {
        movieCache.computeIfPresent(movieId, (id, oldReference) -> {
            log.info("Updating movie in cache by id: {}", movieId);
            return new SoftReference<>(mapper.copy(movieResponse));
        });
    }

    @ManagedOperation
    public String getCachedMovies(){
        if (movieCache.isEmpty()) {
            return "No movies in cache";
        }
        StringBuilder sb = new StringBuilder("Cached movies:\n");
        movieCache.forEach((id, reference) -> {
            if (reference != null && reference.get() != null) {
                sb.append("Movie ID: ").append(id).append(", Title: ").append(Objects.requireNonNull(reference.get()).getNameNative()).append("\n");
            }
        });
        return sb.toString();
    }

}
