package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ReleaseCountryDto {
    private final int id;
    private final String name;
}
