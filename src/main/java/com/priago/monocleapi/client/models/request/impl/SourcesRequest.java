package com.priago.monocleapi.client.models.request.impl;

import com.priago.monocleapi.client.models.request.MonocleClientRequest;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Client.*;

/**
 * A request object for a TopHeadlines NewsAPI call
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class SourcesRequest implements MonocleClientRequest {

    /**
     * Find sources that display news of this category.
     */
    @Getter
    @Setter
    private MonocleCategory category;

    /**
     * Find sources that display news in a specific language.
     */
    @Getter
    @Setter
    private MonocleLanguage language;

    /**
     * Find sources that display news in a specific country.
     */
    @Getter
    @Setter
    private MonocleCountry country;

    @Getter
    private String apiKey;

    @Getter
    private boolean empty = false;

    @Getter
    private boolean valid = false;


    //  CONSTRUCTORS

    public SourcesRequest() {
        emptyInitialize();
    }

    public SourcesRequest(Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            emptyInitialize();
        } else {
            try {
                this.category = MonocleCategory.valueOf(params.getOrDefault(CATEGORY, StringUtils.EMPTY).toUpperCase());
            } catch (Exception e) {
                this.category = null;
            }

            try {
                this.language = MonocleLanguage.valueOf(params.getOrDefault(LANGUAGE, StringUtils.EMPTY).toUpperCase());
            } catch (Exception e) {
                this.language = null;
            }

            try {
                this.country = MonocleCountry.valueOf(params.getOrDefault(COUNTRY, StringUtils.EMPTY).toUpperCase());
            } catch (Exception e) {
                this.country = null;
            }

            this.apiKey = MonocleCoreConstants.Client.getNewsAPIApiKey();
            this.empty = false;
            this.valid = true;
        }
    }


    //  METHODS

    @Override
    public Map<String, Object> getParameterMap() {
        Map<String, Object> params = new HashMap<>();

        if (this.category != null) {
            params.put(CATEGORY, this.category.toString().toLowerCase());
        }

        if (this.language != null) {
            params.put(LANGUAGE, this.language.toString().toLowerCase());
        }

        if (this.country != null) {
            params.put(COUNTRY, this.country.toString().toLowerCase());
        }

        if (StringUtils.isNotEmpty(this.apiKey)) {
            params.put(API_KEY, this.apiKey);
        }

        return params;
    }


    //  HELPERS

    /**
     * Initializes values to defaults
     */
    private void emptyInitialize() {
        this.empty = true;
        this.valid = false;

        this.category = null;
        this.language = null;
        this.country = null;
        this.apiKey = StringUtils.EMPTY;
    }
}
