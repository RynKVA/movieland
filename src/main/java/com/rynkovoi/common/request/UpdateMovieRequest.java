package com.rynkovoi.common.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UpdateMovieRequest {
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private BigDecimal price;
    private String picturePath;
    private List<Integer> countries;
    private List<Integer> genres;
}
