package com.rynkovoi.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderType {

    ASC("asc"),

    DESC("desc");

    private final String name;

    public static OrderType fromString(String orderTypeString) {
        return Arrays.stream(OrderType.values()).filter(orderType -> orderType.getName().equalsIgnoreCase(orderTypeString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No type found for order: %s".formatted(orderTypeString)));
    }
}
