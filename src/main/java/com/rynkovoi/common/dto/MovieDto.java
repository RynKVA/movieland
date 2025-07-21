package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MovieDto {

    private long id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private double rating;
    private BigDecimal price;
    private String picturePath;

}