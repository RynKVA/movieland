package com.rynkovoi.service.impl;

import com.rynkovoi.model.ReleaseCountry;
import com.rynkovoi.repository.ReleaseCountryRepository;
import com.rynkovoi.service.ReleaseCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultReleaseCountryService implements ReleaseCountryService {

    private final ReleaseCountryRepository releaseCountryRepository;

    @Override
    public Set<ReleaseCountry> getAllByIds(List<Integer> ids) {
        return releaseCountryRepository.findByIdIn(ids);
    }
}
