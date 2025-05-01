package com.rynkovoi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParsedMovie {

    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private String releaseCountry;
    private Integer[] genres;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
}