package com.rynkovoi;

import com.rynkovoi.common.response.MovieResponse;
import com.rynkovoi.model.Genre;
import com.rynkovoi.model.Movie;
import com.rynkovoi.model.ReleaseCountry;
import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.MovieDto;

import java.math.BigDecimal;
import java.util.List;

public class ExemplarsCreator {

    public static MovieDto.MovieDtoBuilder createMovieDtoBuilder() {
        return MovieDto.builder()
                .id(1L).nameNative("Movie 1").nameRussian("Фильм 1")
                .yearOfRelease(2020).rating(8.5).price(10.0).picturePath("path/to/picture1.jpg");
    }

    public static List<MovieDto> createMovieDtoListWithThreeMovies() {
        return List.of(
                createMovieDtoBuilder().id(1L).build(),
                createMovieDtoBuilder().id(2L).nameNative("Movie 2").nameRussian("Фильм 2")
                        .yearOfRelease(2021).rating(9.0).price(15.0).picturePath("path/to/picture2.jpg").build(),
                createMovieDtoBuilder().id(3L).nameNative("Movie 3").nameRussian("Фильм 3")
                        .yearOfRelease(2022).rating(7.5).price(12.0).picturePath("path/to/picture3.jpg").build()
        );
    }

    public static List<MovieDto> createMovieDtoListWithFourMovies() {
        return List.of(
                createMovieDtoBuilder().id(1L).build(),
                createMovieDtoBuilder().id(2L).nameNative("Movie 2").nameRussian("Фильм 2")
                        .yearOfRelease(2021).rating(9.0).price(15.0).picturePath("path/to/picture2.jpg").build(),
                createMovieDtoBuilder().id(3L).nameNative("Movie 3").nameRussian("Фильм 3")
                        .yearOfRelease(2022).rating(7.5).price(12.0).picturePath("path/to/picture3.jpg").build(),
                createMovieDtoBuilder().id(4L).nameNative("Movie 4").nameRussian("Фильм 4")
                        .yearOfRelease(2023).rating(8.0).price(20.0).picturePath("path/to/picture4.jpg").build()
        );
    }

    public static List<MovieDto> createMovieDtoListWithFourMoviesSortedByPriceOrderAsc() {
        return List.of(
                createMovieDtoBuilder().id(1L).build(),
                createMovieDtoBuilder().id(3L).nameNative("Movie 3").nameRussian("Фильм 3")
                        .yearOfRelease(2022).rating(7.5).price(12.0).picturePath("path/to/picture3.jpg").build(),
                createMovieDtoBuilder().id(2L).nameNative("Movie 2").nameRussian("Фильм 2")
                        .yearOfRelease(2021).rating(9.0).price(15.0).picturePath("path/to/picture2.jpg").build(),
                createMovieDtoBuilder().id(4L).nameNative("Movie 4").nameRussian("Фильм 4")
                        .yearOfRelease(2023).rating(8.0).price(20.0).picturePath("path/to/picture4.jpg").build()
        );
    }

    public static Movie.MovieBuilder createMovieBuilder() {

        return Movie.builder()
                .id(1L)
                .nameNative("Movie 1")
                .nameRussian("Фильм 1")
                .yearOfRelease(2020)
                .genres(createGenreListWithThreeGenres())
                .rating(8.5)
                .price(BigDecimal.valueOf(10.0))
                .description("Description of Movie 1")
                .releaseCountries(createReleaseCountryListWithThreeCountries());
    }

    public static List<Movie> createMovieListWithThreeMoviesWithSameGenreId() {
        return List.of(
                createMovieBuilder().build(),
                createMovieBuilder()
                        .id(2L)
                        .nameNative("Movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2021)
                        .genres(createGenreListWithThreeGenres())
                        .rating(9.0)
                        .price(BigDecimal.valueOf(15.0))
                        .description("Description of Movie 2")
                        .releaseCountries(createReleaseCountryListWithThreeCountries())
                        .build(),
                createMovieBuilder()
                        .id(3L)
                        .nameNative("Movie 3")
                        .nameRussian("Фильм 3")
                        .yearOfRelease(2022)
                        .genres(createGenreListWithThreeGenres())
                        .rating(7.5)
                        .price(BigDecimal.valueOf(12.0))
                        .description("Description of Movie 3")
                        .releaseCountries(createReleaseCountryListWithThreeCountries())
                        .build()
        );
    }

    public static GenreDto.GenreDtoBuilder createGenreDtoBuilder() {
        return GenreDto.builder()
                .id(1).name("Action");
    }

    public static List<GenreDto> createGenreDtoListWithThreeGenres() {
        return List.of(
                createGenreDtoBuilder().build(),
                createGenreDtoBuilder().id(2).name("Comedy").build(),
                createGenreDtoBuilder().id(3).name("Drama").build()
        );
    }

    public static Genre.GenreBuilder createGenreBuilder() {
        return Genre.builder()
                .id(1)
                .name("Action");
    }

    public static List<Genre> createGenreListWithThreeGenres() {
        return List.of(
                createGenreBuilder().build(),
                createGenreBuilder().id(2).name("Comedy").build(),
                createGenreBuilder().id(3).name("Drama").build()
        );
    }

    public static List<ReleaseCountry> createReleaseCountryListWithThreeCountries() {
        return List.of(
                ReleaseCountry.builder().id(1).name("USA").build(),
                ReleaseCountry.builder().id(2).name("UK").build(),
                ReleaseCountry.builder().id(3).name("France").build()
        );
    }

    public static MovieResponse.MovieResponseBuilder createMovieResponseBuilder() {
        return MovieResponse.builder()
                .id(1L)
                .nameNative("Movie 1")
                .nameRussian("Фильм 1")
                .yearOfRelease(2020)
                .rating(8.5)
                .price(BigDecimal.valueOf(10.99))
                .picturePath("path/to/picture1.jpg");
    }
}