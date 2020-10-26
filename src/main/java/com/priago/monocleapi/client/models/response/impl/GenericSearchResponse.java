package com.priago.monocleapi.client.models.response.impl;

import com.priago.monocleapi.client.models.data.impl.ArticleResponseData;
import com.priago.monocleapi.client.models.response.MonocleClientResponse;
import com.priago.monocleapi.core.enums.MonocleStatus;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 *
 * A generic response from NewsAPI when querying the main endpoints
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class GenericSearchResponse implements MonocleClientResponse {

    @Getter
    private final MonocleStatus status;

    @Getter
    private final int totalResults;

    @Getter
    private final List<ArticleResponseData> articles;

    @Getter
    private final boolean empty;

    @Getter
    private final boolean valid;

    @Getter
    private final String code;

    @Getter
    private final String message;


    //  CONSTRUCTORS

    public GenericSearchResponse() {
        this.status = null;
        this.totalResults = -1;
        this.articles = Collections.emptyList();
        this.code = StringUtils.EMPTY;
        this.message = "Empty or No Response";
        this.empty = true;
        this.valid = false;
    }

    public GenericSearchResponse(MonocleStatus status, int totalResults, List<ArticleResponseData> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
        this.code = StringUtils.EMPTY;
        this.message = StringUtils.EMPTY;
        this.empty = false;
        this.valid = true;
    }

    public GenericSearchResponse(MonocleStatus status, int totalResults, List<ArticleResponseData> articles, String code, String message) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
        this.code = code;
        this.message = message;
        this.empty = false;
        this.valid = !StringUtils.isNotEmpty(code) || !StringUtils.isNotEmpty(message);
    }
}
