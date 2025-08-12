package com.rynkovoi.service.impl;

import com.rynkovoi.common.dto.ReviewDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.ReviewRepository;
import com.rynkovoi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DefaultReviewService  implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CommonMapper mapper;

    @Override
    public List<ReviewDto> findByMovieId(long id) {
        return mapper.toReviewDtos(reviewRepository.findWithUserByMovieId(id));
    }

    @Override
    @Async("enrichmentExecutor")
    public CompletableFuture<List<ReviewDto>> findAsyncByMovieId(long movieId) {
        return CompletableFuture.supplyAsync(() -> mapper.toReviewDtos(reviewRepository.findWithUserByMovieId(movieId)));
    }
}
