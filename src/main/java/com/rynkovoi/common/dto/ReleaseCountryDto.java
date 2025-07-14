package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReleaseCountryDto {
    private int id;
    private String name;
}
