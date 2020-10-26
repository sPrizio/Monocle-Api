package com.priago.monocleapi.client.builder.request;

import com.priago.monocleapi.client.models.request.MonocleClientRequest;
import com.priago.monocleapi.client.models.request.impl.EverythingRequest;
import com.priago.monocleapi.client.models.request.impl.SourcesRequest;
import com.priago.monocleapi.client.models.request.impl.TopHeadlinesRequest;

import java.util.Map;

/**
 * Builds {@link MonocleClientRequest}s
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleClientRequestBuilder {

    /**
     * Builds a {@link TopHeadlinesRequest}
     *
     * @param params map of parameters to build the request with
     * @return a {@link TopHeadlinesRequest}
     */
    TopHeadlinesRequest buildTopHeadlinesRequest(Map<String, Object> params);

    /**
     * Builds a {@link EverythingRequest}
     *
     * @param params map of parameters to build the request with
     * @return a {@link EverythingRequest}
     */
    EverythingRequest buildEverythingRequest(Map<String, Object> params);

    /**
     * Builds a {@link SourcesRequest}
     *
     * @param params map of parameters to build the request with
     * @return a {@link SourcesRequest}
     */
    SourcesRequest buildSourcesRequest(Map<String, Object> params);
}
