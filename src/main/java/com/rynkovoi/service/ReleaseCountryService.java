package com.rynkovoi.service;

import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.model.ReleaseCountry;

import java.util.List;
import java.util.Set;

public interface ReleaseCountryService {

    Set<ReleaseCountry> getAllByIds(List<Integer> ids);

    boolean isValid(Set<ReleaseCountryDto> countries);

    List<ReleaseCountryDto> findByMovieId(long id);
}
