package com.priago.monocleapi.client.models.data.impl;

import com.priago.monocleapi.client.models.data.MonocleResponseData;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import lombok.*;

/**
 * A data class modelling a source, a entity that publishes {@link ArticleResponseData}, from NewsAPI
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class SourceResponseData implements MonocleResponseData {

    @Getter
    @NonNull
    private String id;

    @Getter
    @NonNull
    private String name;

    @Getter
    private String description;

    @Getter
    private String url;

    @Getter
    private MonocleCategory category;

    @Getter
    private MonocleLanguage language;

    @Getter
    private MonocleCountry country;
}
