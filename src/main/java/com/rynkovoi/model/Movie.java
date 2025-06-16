package com.rynkovoi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private long id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private String releaseCountry;
    private Integer[] genres;
    private String description;
    private double rating;
    private double price;
    private String poster;
}
