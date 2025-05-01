package com.rynkovoi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SortType {

    Rating("rating"),
    Price("price");

    private final String filterBy;

    public static SortType fromString(String filterBy) {
        return Arrays.stream(SortType.values()).filter(sortByType -> sortByType.getFilterBy().equalsIgnoreCase(filterBy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant found for filterBy: %s".formatted(filterBy)));
    }
}