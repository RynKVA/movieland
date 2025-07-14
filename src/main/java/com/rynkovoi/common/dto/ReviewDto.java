package com.rynkovoi.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {

    private Long id;

    private UserDto user;

    private String text;
}
