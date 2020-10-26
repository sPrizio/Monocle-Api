package com.priago.monocleapi.client.newsapi;

import com.priago.monocleapi.client.models.request.impl.EverythingRequest;
import com.priago.monocleapi.client.models.request.impl.SourcesRequest;
import com.priago.monocleapi.client.models.request.impl.TopHeadlinesRequest;
import com.priago.monocleapi.client.models.response.impl.GenericSearchResponse;
import com.priago.monocleapi.client.models.response.impl.SourcesResponse;

/**
 * Implementation of a Java client for interacting with NewsAPI.
 * Handles endpoint fetch calls to NewsAPI for obtaining data. Much of the documentation here is lifted from
 * the NewsAPI documentation.
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleNewsAPIClient {

    /**
     * Returns breaking news headlines for a country and category, or currently running on a single or multiple sources.
     *
     * @param request a {@link TopHeadlinesRequest}
     * @return a {@link GenericSearchResponse}
     */
    GenericSearchResponse fetchTopHeadlines(TopHeadlinesRequest request);

    /**
     * Every recent news and blog article published by over 50,000 different sources large and small.
     *
     * @param request a {@link EverythingRequest}
     * @return a {@link GenericSearchResponse}
     */
    GenericSearchResponse fetchEverything(EverythingRequest request);

    /**
     * Returns information (including name, description, and category) about the most notable sources indexed.
     *
     * @param request a {@link SourcesRequest}
     * @return a {@link SourcesResponse}
     */
    SourcesResponse fetchSources(SourcesRequest request);
}
