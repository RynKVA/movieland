package com.rynkovoi.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jooq.SortOrder;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    ASC("asc", SortOrder.ASC),
    DESC("desc", SortOrder.DESC);

    private final String value;
    private final SortOrder sortOrder;

    public static OrderType fromValue(String value) {
        for (OrderType orderType : values()) {
            if (orderType.value.equalsIgnoreCase(value)) {
                return orderType;
            }
        }
        throw new IllegalArgumentException("Unknown order type: " + value);
    }
}
