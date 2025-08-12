package com.rynkovoi.service;

import com.rynkovoi.common.dto.ReviewDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ReviewService {

    List<ReviewDto> findByMovieId(long id);

    CompletableFuture<List<ReviewDto>> findAsyncByMovieId(long movieId);
}
