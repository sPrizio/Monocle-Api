package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Monocle client response statuses
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum MonocleStatus {

    OK("ok", "Ok"),
    ERROR("error", "Error");

    @Getter
    private final String code;

    @Getter
    private final String name;

    MonocleStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
