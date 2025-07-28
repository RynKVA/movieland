package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {

    private final Long id;
    private final UserDto user;
    private final String text;
}
