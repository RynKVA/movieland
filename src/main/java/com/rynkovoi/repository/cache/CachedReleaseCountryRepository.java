package com.rynkovoi.repository.cache;

import com.rynkovoi.mapper.CommonMapper;
import com.rynkovoi.repository.ReleaseCountryRepository;
import com.rynkovoi.repository.jpa.JpaReleaseCountryRepository;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository
public class CachedReleaseCountryRepository extends AbstractCachedRepository<ReleaseCountryDto> implements ReleaseCountryRepository {

    private final CommonMapper mapper;
    private final JpaReleaseCountryRepository releaseCountryRepository;

    @Override
    protected List<ReleaseCountryDto> fillCache() {
        return mapper.toReleaseCountryDtos(releaseCountryRepository.findAll());
    }
}
