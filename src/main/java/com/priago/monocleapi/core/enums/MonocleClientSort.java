package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Supported sorting parameters used by NewsAPI
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum MonocleClientSort {

    RELEVANCY("relevancy", "Relevancy"),
    POPULARITY("popularity", "Popularity"),
    PUBLISHED_AT("published_at", "Published At");

    @Getter
    private final String code;

    @Getter
    private final String name;

    MonocleClientSort(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
