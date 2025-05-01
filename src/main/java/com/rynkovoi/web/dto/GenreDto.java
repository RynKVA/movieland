package com.rynkovoi.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreDto {
    private int id;
    private String name;
}