package com.priago.monocleapi.client.models.request.impl;

import com.priago.monocleapi.client.models.request.MonocleClientRequest;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.util.MonocleNumberUtils;
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
public class TopHeadlinesRequest implements MonocleClientRequest {

    /**
     * The 2-letter ISO 3166-1 code of the country you want to get headlines for. This parameter cannot be mixed with sources.
     */
    @Getter
    @Setter
    private MonocleCountry country;

    /**
     * The category you want to get headlines for. This parameter cannot be mixed with sources.
     */
    @Getter
    @Setter
    private MonocleCategory category;

    /**
     * A comma-separated string of identifiers for the news sources or blogs you want headlines from.
     */
    @Getter
    @Setter
    private String sources;

    /**
     * Keywords or a phrase to search for.
     */
    @Getter
    @Setter
    private String q;

    /**
     * The number of results to return per page (request). 20 is the default, 100 is the maximum.
     */
    @Getter
    @Setter
    private int pageSize;

    /**
     * Use this to page through the results if the total results found is greater than the page size.
     */
    @Getter
    @Setter
    private int page;

    @Getter
    private String apiKey;

    @Getter
    private boolean empty;

    @Getter
    private boolean valid;


    //  CONSTRUCTORS

    public TopHeadlinesRequest() {
        emptyInitialize();
    }

    public TopHeadlinesRequest(Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            emptyInitialize();
        } else {
            try {
                this.country = MonocleCountry.valueOf(params.getOrDefault(COUNTRY, StringUtils.EMPTY).toUpperCase());
            } catch (Exception e) {
                this.country = null;
            }

            try {
                this.category = MonocleCategory.valueOf(params.getOrDefault(CATEGORY, StringUtils.EMPTY).toUpperCase());
            } catch (Exception e) {
                this.category = null;
            }

            this.sources = params.getOrDefault(SOURCES, StringUtils.EMPTY);
            this.q = params.getOrDefault(Q, StringUtils.EMPTY);
            this.pageSize = MonocleNumberUtils.safeParseInteger(params.getOrDefault(PAGE_SIZE, StringUtils.EMPTY));
            this.page = MonocleNumberUtils.safeParseInteger(params.getOrDefault(PAGE, StringUtils.EMPTY));
            this.apiKey = MonocleCoreConstants.Client.getNewsAPIApiKey();

            validateRequest();
        }
    }


    //  METHODS

    @Override
    public Map<String, Object> getParameterMap() {
        Map<String, Object> params = new HashMap<>();

        if (this.country != null) {
            params.put(COUNTRY, this.country.toString().toLowerCase());
        }

        if (this.category != null) {
            params.put(CATEGORY, this.category.toString().toLowerCase());
        }

        if (StringUtils.isNotEmpty(this.sources)) {
            params.put(SOURCES, this.sources);
        }

        if (StringUtils.isNotEmpty(this.q)) {
            params.put(Q, this.q);
        }

        if (this.pageSize != -1) {
            params.put(PAGE_SIZE, this.pageSize);
        }

        if (this.page != -1) {
            params.put(PAGE, this.page);
        }

        if (StringUtils.isNotEmpty(this.apiKey)) {
            params.put(API_KEY, this.apiKey);
        }

        return params;
    }


    //  HELPERS

    /**
     * Validates nuances in the request as dictated by NewsAPI
     */
    private void validateRequest() {
        if (StringUtils.isNotEmpty(this.sources)) {
            this.valid = this.country == null && this.category == null;
        } else {
            this.valid = true;
        }
    }

    /**
     * Initializes values to defaults
     */
    private void emptyInitialize() {
        this.empty = true;
        this.valid = false;

        this.country = null;
        this.category = null;
        this.sources = StringUtils.EMPTY;
        this.q = StringUtils.EMPTY;
        this.pageSize = -1;
        this.page = -1;
        this.apiKey = StringUtils.EMPTY;
    }
}
