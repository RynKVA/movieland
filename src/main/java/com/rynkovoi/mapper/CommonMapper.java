package com.rynkovoi.mapper;


import com.rynkovoi.common.dto.ReviewDto;
import com.rynkovoi.model.Genre;
import com.rynkovoi.model.Movie;
import com.rynkovoi.model.ReleaseCountry;
import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.model.Review;
import com.rynkovoi.common.response.MovieResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface CommonMapper {

    @Mapping(target = "picturePath", source = "poster")
    MovieDto toMovieDto(Movie movie);

    @Mapping(target = "picturePath", source = "movie.poster")
    @Mapping(target = "countries", source = "movie.releaseCountries")
    @Mapping(target = "price", source = "convertedPrice")
    MovieResponse toMovieResponse(Movie movie, BigDecimal convertedPrice);

    GenreDto toGenreDto(Genre genres);

    ReleaseCountryDto toReleaseCountryDto(ReleaseCountry releaseCountry);

    ReviewDto toReviewDto(Review review);

    List<MovieDto> toMovieDtos(List<Movie> movies);

    List<GenreDto> toGenreDtos(List<Genre> genres);

    List<ReleaseCountryDto> toReleaseCountryDtos(List<ReleaseCountry> releaseCountries);
}