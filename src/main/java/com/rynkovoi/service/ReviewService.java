package com.rynkovoi.service;

import com.rynkovoi.common.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    List<ReviewDto> findByMovieId(long id);
}
