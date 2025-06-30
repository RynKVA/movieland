package com.rynkovoi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SortType {

    RATING("rating"),
    PRICE("price");

    private final String name;

    public static SortType fromString(String sortTypeString) {
        return Arrays.stream(SortType.values()).filter(sortByType -> sortByType.getName().equalsIgnoreCase(sortTypeString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No type found for filterBy: %s".formatted(sortTypeString)));
    }
}