package com.rynkovoi.service.cache;

import com.rynkovoi.common.response.MovieResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieCache {

    private final Map<Long, SoftReference<MovieResponse>> movieCache = new ConcurrentHashMap<>();

    public MovieResponse save(MovieResponse movieResponse) {
        log.info("Saving movie to cache with id: {}", movieResponse.getId());
        movieCache.put(movieResponse.getId(), new SoftReference<>(movieResponse));
        return movieResponse;
    }

    public MovieResponse get(long movieId) {
        log.info("Retrieving movie from cache by id: {}", movieId);
        MovieResponse movieResponse = movieCache.get(movieId).get();
        if (movieResponse == null) {
            movieCache.remove(movieId);
        }
        return movieResponse;
    }

    public void update (long movieId, MovieResponse movieResponse) {
        if (!movieCache.containsKey(movieId)) {
            log.warn("Updating a movie failed due it does not exist in cache: {}", movieId);
        }
        log.info("Updating movie in cache by id: {}", movieId);
        movieCache.put(movieId, new SoftReference<>(movieResponse));
    }

}
