package com.rynkovoi.common.response;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import com.rynkovoi.common.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class MovieResponse {

    private long id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private String description;
    private double rating;
    private BigDecimal price;
    private String picturePath;

    private List<ReleaseCountryDto> countries;
    private List<GenreDto> genres;
    private List<ReviewDto> reviews;
}
