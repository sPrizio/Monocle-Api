package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Possible states that a validation result can be in as a result of validation
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum ValidationResponseResult {

    FAILED("failed", "Failed"),
    SUCCESSFUL("successful", "Successful");

    @Getter
    private final String code;

    @Getter
    private final String name;

    ValidationResponseResult(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
