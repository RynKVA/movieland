package com.rynkovoi.mapper;


import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.MovieDto;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.common.dto.ReviewDto;
import com.rynkovoi.common.request.MovieRequest;
import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.model.Genre;
import com.rynkovoi.model.Movie;
import com.rynkovoi.model.ReleaseCountry;
import com.rynkovoi.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "picturePath", source = "movie.poster")
    MovieResponse toTrimmedMovieResponse(Movie movie);

    @Mapping(target = "poster", source = "picturePath")
    @Mapping(target = "releaseCountries", source = "countries")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Movie toMovie(MovieRequest movieRequest);

    @Mapping(target = "poster", source = "picturePath")
    @Mapping(target = "releaseCountries", source = "countries")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Movie update(MovieRequest movieRequest, @MappingTarget Movie movie);

    GenreDto toGenreDto(Genre genres);

    ReleaseCountryDto toReleaseCountryDto(ReleaseCountry releaseCountry);

    ReviewDto toReviewDto(Review review);

    List<MovieDto> toMovieDtos(List<Movie> movies);

    List<GenreDto> toGenreDtos(List<Genre> genres);

    List<ReleaseCountryDto> toReleaseCountryDtos(List<ReleaseCountry> releaseCountries);

    List<ReviewDto> toReviewDtos(List<Review> reviews);
}