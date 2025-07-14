package com.rynkovoi.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SortDirection {

    ASC("asc"),

    DESC("desc");

    private final String name;

    public static SortDirection fromString(String sortDirectionString) {
        return Arrays.stream(SortDirection.values()).filter(sortDirection -> sortDirection.getName().equalsIgnoreCase(sortDirectionString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No type found for direction: %s".formatted(sortDirectionString)));
    }
}
