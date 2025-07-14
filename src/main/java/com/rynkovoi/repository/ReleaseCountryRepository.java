package com.rynkovoi.repository;

import com.rynkovoi.common.dto.ReleaseCountryDto;

import java.util.List;

public interface ReleaseCountryRepository {

    List<ReleaseCountryDto> findAllDto();
}
