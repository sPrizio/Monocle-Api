package com.priago.monocleapi.client.models.request.impl;

import com.priago.monocleapi.client.models.request.MonocleClientRequest;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleClientSort;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.util.MonocleNumberUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Client.*;

/**
 * A request object for an Everything NewsAPI call
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class EverythingRequest implements MonocleClientRequest {

    /**
     * Keywords or phrases to search for in the article title and body.
     * Advanced search is supported here:
     * <ul>
     *     <ol>Surround phrases with quotes (") for exact match.</ol>
     *     <ol>Prepend words or phrases that must appear with a + symbol. Eg: +bitcoin</ol>
     *     <ol>Prepend words that must not appear with a - symbol. Eg: -bitcoin</ol>
     *     <ol>Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis. Eg: crypto AND (ethereum OR litecoin) NOT bitcoin.</ol>
     * </ul>
     * The complete value for q must be <strong>URL-encoded</strong>.
     */
    @Getter
    @Setter
    private String q;

    /**
     * Keywords or phrases to search for in the article title only.
     * Advanced search is supported here:
     * <ul>
     *     <ol>Surround phrases with quotes (") for exact match.</ol>
     *     <ol>Prepend words or phrases that must appear with a + symbol. Eg: +bitcoin</ol>
     *     <ol>Prepend words that must not appear with a - symbol. Eg: -bitcoin</ol>
     *     <ol>Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis. Eg: crypto AND (ethereum OR litecoin) NOT bitcoin.</ol>
     * </ul>
     * The complete value for q must be <strong>URL-encoded</strong>.
     */
    @Getter
    @Setter
    private String qInTitle;

    /**
     * A comma-separated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
     */
    @Getter
    @Setter
    private String sources;

    /**
     * A comma-separated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
     */
    @Getter
    @Setter
    private String domains;

    /**
     * A comma-separated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to remove from the results.
     */
    @Getter
    @Setter
    private String excludeDomains;

    /**
     * A date and optional time for the oldest article allowed. This should be in ISO 8601 format (e.g. 2020-10-13 or 2020-10-13T16:00:12) Default: the oldest according to your plan.
     */
    @Getter
    @Setter
    private String from;

    /**
     * A date and optional time for the newest article allowed. This should be in ISO 8601 format (e.g. 2020-10-13 or 2020-10-13T16:00:12) Default: the newest according to your plan.
     */
    @Getter
    @Setter
    private String to;

    /**
     * The 2-letter ISO-639-1 code of the language you want to get headlines for.
     */
    @Getter
    @Setter
    private MonocleLanguage language;

    /**
     * The order to sort the articles in.
     */
    @Getter
    @Setter
    private MonocleClientSort sortBy;

    /**
     * The number of results to return per page. 20 is the default, 100 is the maximum.
     */
    @Getter
    @Setter
    private int pageSize;

    /**
     * Use this to page through the results.
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

    public EverythingRequest() {
        emptyInitialize();
    }

    public EverythingRequest(Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            emptyInitialize();
        } else {
            this.q = params.getOrDefault(Q, StringUtils.EMPTY);
            this.qInTitle = params.getOrDefault(Q_IN_TITLE, StringUtils.EMPTY);
            this.sources = params.getOrDefault(SOURCES, StringUtils.EMPTY);
            this.domains = params.getOrDefault(DOMAINS, StringUtils.EMPTY);
            this.excludeDomains = params.getOrDefault(EXCLUDE_DOMAINS, StringUtils.EMPTY);
            this.from = params.getOrDefault(FROM, StringUtils.EMPTY);
            this.to = params.getOrDefault(TO, StringUtils.EMPTY);

            try {
                this.language = MonocleLanguage.valueOf(params.getOrDefault(LANGUAGE, StringUtils.EMPTY));
            } catch (Exception e) {
                this.language = null;
            }

            try {
                this.sortBy = MonocleClientSort.valueOf(params.getOrDefault(SORT_BY, StringUtils.EMPTY));
            } catch (Exception e) {
                this.sortBy = null;
            }

            this.pageSize = MonocleNumberUtils.safeParseInteger(params.getOrDefault(PAGE_SIZE, StringUtils.EMPTY));
            this.page = MonocleNumberUtils.safeParseInteger(params.getOrDefault(PAGE, StringUtils.EMPTY));
            this.apiKey = MonocleCoreConstants.Client.getNewsAPIApiKey();

            this.empty = false;
            this.valid = true;
        }
    }


    //  METHODS

    @Override
    public Map<String, Object> getParameterMap() {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotEmpty(this.q)) {
            params.put(Q, this.q);
        }

        if (StringUtils.isNotEmpty(this.qInTitle)) {
            params.put(Q_IN_TITLE, this.qInTitle);
        }

        if (StringUtils.isNotEmpty(this.sources)) {
            params.put(SOURCES, this.sources);
        }

        if (StringUtils.isNotEmpty(this.domains)) {
            params.put(DOMAINS, this.domains);
        }

        if (StringUtils.isNotEmpty(this.excludeDomains)) {
            params.put(EXCLUDE_DOMAINS, this.excludeDomains);
        }

        if (StringUtils.isNotEmpty(this.from)) {
            params.put(FROM, this.from);
        }

        if (StringUtils.isNotEmpty(this.to)) {
            params.put(TO, this.to);
        }

        if (this.language != null) {
            params.put(LANGUAGE, this.language);
        }

        if (this.sortBy != null) {
            params.put(SORT_BY, this.sortBy);
        }

        if (getPageSize() != -1) {
            params.put(PAGE_SIZE, this.pageSize);
        }

        if (getPage() != -1) {
            params.put(PAGE, this.page);
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

        this.q = StringUtils.EMPTY;
        this.qInTitle = StringUtils.EMPTY;
        this.sources = StringUtils.EMPTY;
        this.domains = StringUtils.EMPTY;
        this.excludeDomains = StringUtils.EMPTY;
        this.from = StringUtils.EMPTY;
        this.to = StringUtils.EMPTY;
        this.language = null;
        this.sortBy = null;
        this.pageSize = -1;
        this.page = -1;
        this.apiKey = StringUtils.EMPTY;
    }
}
