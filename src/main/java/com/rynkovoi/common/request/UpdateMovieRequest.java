package com.rynkovoi.common.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UpdateMovieRequest {
    private String nameRussian;
    private String nameNative;
    private String picturePath;
    private List<Integer> countries;
    private List<Integer> genres;
}
