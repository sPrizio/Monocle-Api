package com.priago.monocleapi.client.builder.response;

import com.priago.monocleapi.client.models.response.MonocleClientResponse;
import com.priago.monocleapi.client.models.response.impl.GenericSearchResponse;
import com.priago.monocleapi.client.models.response.impl.SourcesResponse;

import java.net.http.HttpResponse;

/**
 * Produces {@link MonocleClientResponse}s from {@link HttpResponse}s
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleClientResponseBuilder {

    /**
     * Converts an {@link HttpResponse} into a {@link GenericSearchResponse}
     *
     * @param response the response from calling NewsAPI
     * @return a {@link GenericSearchResponse}
     */
    GenericSearchResponse handleGenericSearchResponse(String response);

    /**
     * Converts an {@link HttpResponse} into a {@link SourcesResponse}
     *
     * @param response the response from calling NewsAPI
     * @return a {@link SourcesResponse}
     */
    SourcesResponse handleSourcesResponse(String response);
}
