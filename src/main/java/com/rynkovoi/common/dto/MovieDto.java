package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MovieDto {

    private final long id;
    private final String nameNative;
    private final String nameRussian;
    private final int yearOfRelease;
    private final double rating;
    private final BigDecimal price;
    private final String picturePath;

}