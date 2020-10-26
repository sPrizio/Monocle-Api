package com.priago.monocleapi.core.enums;

import lombok.Getter;

/**
 * Supported countries by Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum MonocleCountry {

    NOT_APPLICABLE("N/A", "Not Applicable"),
    AE("ae", "United Arab Emirates"),
    AR("ar", "Argentina"),
    AT("at", "Austria"),
    AU("au", "Australia"),
    BE("be", "Belgium"),
    BG("bg", "Bulgaria"),
    BR("br", "Brazil"),
    CA("ca", "Canada"),
    CH("ch", "Switzerland"),
    CN("cn", "China"),
    CO("co", "Colombia"),
    CU("cu", "Cuba"),
    CZ("cz", "Czech Republic"),
    DE("de", "Germany"),
    EG("eg", "Egypt"),
    FR("fr", "France"),
    GB("gb", "Great Britain"),
    GR("gr", "Greece"),
    HK("hk", "Hong Kong"),
    HU("hu", "Hungary"),
    ID("id", "Indonesia"),
    IE("ie", "Ireland"),
    IL("il", "Israel"),
    IN("in", "India"),
    IT("it", "Italy"),
    JP("jp", "Japan"),
    KR("kr", "Korea"),
    LT("lt", "Lithuania"),
    LV("lv", "Latvia"),
    MA("ma", "Morocco"),
    MX("mx", "Mexico"),
    MY("my", "Malaysia"),
    NG("ng", "Nigeria"),
    NL("nl", "Netherlands"),
    NO("no", "Norway"),
    NZ("nz", "New Zealand"),
    PH("ph", "Philippines"),
    PL("pl", "Poland"),
    PT("pt", "Portugal"),
    RO("ro", "Romania"),
    RS("rs", "Serbia"),
    RU("ru", "Russia"),
    SA("sa", "Saudi Arabia"),
    SG("sg", "Singapore"),
    SI("si", "Slovenia"),
    SK("sk", "Slovakia"),
    TH("th", "Thailand"),
    TR("tr", "Turkey"),
    TW("tw", "Taiwan"),
    UA("ua", "Ukraine"),
    US("us", "United States"),
    VE("ve", "Venezuela"),
    ZA("za", "South Africa");

    @Getter
    private final String code;

    @Getter
    private final String name;

    MonocleCountry(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
