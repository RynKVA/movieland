package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreDto {
    private int id;
    private String name;
}