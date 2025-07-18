package com.rynkovoi.common;

import com.rynkovoi.type.SortDirection;
import com.rynkovoi.type.SortType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieFilter {
    private SortType sortType;
    private SortDirection sortDirection;
    private int page;
    private int size;
    private String searchText;
}
