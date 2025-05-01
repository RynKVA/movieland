package com.rynkovoi.service;

import com.rynkovoi.web.dto.GenreDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GenreService {

    void saveGenres(List<GenreDto> genres);

    List<GenreDto> getAllGenres();

    GenreDto getGenre(int id);

    void saveParsedGenreNames(MultipartFile file);
}