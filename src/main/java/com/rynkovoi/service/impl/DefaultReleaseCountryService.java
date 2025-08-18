package com.rynkovoi.service.impl;

import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.model.ReleaseCountry;
import com.rynkovoi.repository.ReleaseCountryRepository;
import com.rynkovoi.service.ReleaseCountryService;
import com.rynkovoi.service.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultReleaseCountryService implements ReleaseCountryService {

    private final Cache<ReleaseCountryDto> cache;
    private final ReleaseCountryRepository releaseCountryRepository;
    private final CommonMapper mapper;

    @Override
    public Set<ReleaseCountry> getAllByIds(List<Integer> ids) {
        return releaseCountryRepository.findByIdIn(ids);
    }

    @Override
    public List<ReleaseCountryDto> findByMovieId(long id) {
        return mapper.toReleaseCountryDtos(releaseCountryRepository.findByMovieId(id));
    }

    @Override
    public boolean isValid(Set<ReleaseCountryDto> countries) {
        return countries.stream()
                .allMatch(cache::isExist);
    }
}
