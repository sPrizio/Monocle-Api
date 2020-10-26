package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Defines the sort order, ascending or descending
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum SortOrder {

    ASCENDING("asc", "Ascending"),
    DESCENDING("desc", "Descending");

    @Getter
    private final String code;

    @Getter
    private final String name;

    SortOrder(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
