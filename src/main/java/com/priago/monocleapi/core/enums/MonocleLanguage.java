package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Supported languages by Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum MonocleLanguage {

    NOT_APPLICABLE("N/A", "Not Applicable"),
    AR("ar", "Arabic"),
    DE("de", "German"),
    EN("en", "English"),
    ES("es", "Spanish"),
    FR("fr", "French"),
    HE("he", "Hebrew"),
    IT("it", "Italian"),
    NL("nl", "Dutch"),
    NO("no", "Norwegian/Norsk"),
    PT("pt", "Portuguese"),
    RU("ru", "Russian"),
    SE("se", "Northern Sami"),
    ZH("zh", "Chinese");

    @Getter
    private final String code;

    @Getter
    private final String name;

    MonocleLanguage(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
