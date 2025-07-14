package com.rynkovoi.service.impl;

import com.rynkovoi.repository.GenreRepository;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.common.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenresService implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> getAll() {
        return genreRepository.findAllDto();
    }
}