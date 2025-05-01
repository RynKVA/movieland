package com.rynkovoi.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieDto {

    private long id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private double rating;
    private double price;
    private String picturePath;

}