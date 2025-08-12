package com.rynkovoi.service;

import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.model.ReleaseCountry;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ReleaseCountryService {

    Set<ReleaseCountry> getAllByIds(List<Integer> ids);

    boolean isValid(Set<ReleaseCountryDto> countries);

    List<ReleaseCountryDto> findByMovieId(long id);

    CompletableFuture<List<ReleaseCountryDto>> findAsyncByMovieId(long movieId);
}
