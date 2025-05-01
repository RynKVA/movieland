package com.rynkovoi;

import com.rynkovoi.web.dto.GenreDto;
import com.rynkovoi.web.dto.MovieDto;
import generated.tables.records.GenresRecord;
import generated.tables.records.MoviesRecord;

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

    public static MoviesRecord createMovieRecord() {
        return new MoviesRecord()
                .setId(1L)
                .setNameNative("Movie 1")
                .setNameRussian("Фильм 1")
                .setYearOfRelease(2020)
                .setGenres(new Integer[]{1, 2})
                .setRating(8.5)
                .setPrice(10.0)
                .setDescription("Description of Movie 1")
                .setReleaseCountry("USA");
    }

    public static List<MoviesRecord> createMovieRecordListWithThreeMoviesWithSameGenreId() {
        return List.of(
                createMovieRecord(),
                createMovieRecord()
                        .setId(2L)
                        .setNameNative("Movie 2")
                        .setNameRussian("Фильм 2")
                        .setYearOfRelease(2021)
                        .setGenres(new Integer[]{1, 3})
                        .setRating(9.0)
                        .setPrice(15.0)
                        .setDescription("Description of Movie 2")
                        .setReleaseCountry("USA"),
                createMovieRecord()
                        .setId(3L)
                        .setNameNative("Movie 3")
                        .setNameRussian("Фильм 3")
                        .setYearOfRelease(2022)
                        .setGenres(new Integer[]{1, 3})
                        .setRating(7.5)
                        .setPrice(12.0)
                        .setDescription("Description of Movie 3")
                        .setReleaseCountry("USA")
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

    public static GenresRecord createGenreRecord() {
        return new GenresRecord()
                .setId(1)
                .setName("Action");
    }

    public static List<GenresRecord> createGenreRecordListWithThreeGenres() {
        return List.of(
                createGenreRecord(),
                createGenreRecord().setId(2).setName("Comedy"),
                createGenreRecord().setId(3).setName("Drama")
        );
    }
}