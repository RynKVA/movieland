package com.rynkovoi.common.request;

import com.rynkovoi.common.dto.GenreDto;
import com.rynkovoi.common.dto.ReleaseCountryDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class MovieRequest {
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private BigDecimal price;
    private String picturePath;
    private Set<ReleaseCountryDto> countries;
    private Set<GenreDto> genres;
}
