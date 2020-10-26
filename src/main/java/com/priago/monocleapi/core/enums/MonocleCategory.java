package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Supported categories by Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum MonocleCategory {

    NOT_APPLICABLE("N/A", "Not Applicable"),
    BUSINESS("business", "Business"),
    ENTERTAINMENT("entertainment", "Entertainment"),
    GENERAL("general", "General"),
    HEALTH("health", "Health"),
    SCIENCE("science", "Science"),
    SPORTS("sports", "Sports"),
    TECHNOLOGY("technology", "Technology");

    @Getter
    private final String code;

    @Getter
    private final String name;

    MonocleCategory(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
