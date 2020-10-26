package com.priago.monocleapi.client.models.response.impl;

import com.priago.monocleapi.client.models.data.impl.SourceResponseData;
import com.priago.monocleapi.client.models.response.MonocleClientResponse;
import com.priago.monocleapi.core.enums.MonocleStatus;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * A response when querying NewsAPI for sources
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class SourcesResponse implements MonocleClientResponse {

    @Getter
    private final MonocleStatus status;

    @Getter
    private final List<SourceResponseData> sources;

    @Getter
    private final boolean empty;

    @Getter
    private final boolean valid;

    @Getter
    private final String code;

    @Getter
    private final String message;


    //  CONSTRUCTORS

    public SourcesResponse() {
        this.status = null;
        this.sources = Collections.emptyList();
        this.code = StringUtils.EMPTY;
        this.message = "Empty or No Response";
        this.empty = true;
        this.valid = false;
    }

    public SourcesResponse(MonocleStatus status, List<SourceResponseData> sources) {
        this.status = status;
        this.sources = sources;
        this.code = StringUtils.EMPTY;
        this.message = StringUtils.EMPTY;
        this.empty = false;
        this.valid = true;
    }

    public SourcesResponse(MonocleStatus status, List<SourceResponseData> sources, String code, String message) {
        this.status = status;
        this.sources = sources;
        this.code = code;
        this.message = message;
        this.empty = false;
        this.valid = status == null || status.equals(MonocleStatus.ERROR);
    }
}
