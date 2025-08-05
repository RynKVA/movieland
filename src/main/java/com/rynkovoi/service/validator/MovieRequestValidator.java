package com.rynkovoi.service.validator;

import com.rynkovoi.common.request.MovieRequest;
import com.rynkovoi.exception.MovieRequestNotValidException;
import com.rynkovoi.service.GenreService;
import com.rynkovoi.service.ReleaseCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieRequestValidator {

    private final GenreService genreService;
    private final ReleaseCountryService releaseCountryService;

    public void validateMovieRequest(MovieRequest request) {
        if (!genreService.isValid(request.getGenres()) || !releaseCountryService.isValid(request.getCountries())) {
            throw new MovieRequestNotValidException("Invalid genres or countries in request");
        }
    }
}
