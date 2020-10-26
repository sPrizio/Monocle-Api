package com.priago.monocleapi.client.newsapi.impl;

import com.priago.monocleapi.client.builder.response.MonocleClientResponseBuilder;
import com.priago.monocleapi.client.models.request.MonocleClientRequest;
import com.priago.monocleapi.client.models.request.impl.EverythingRequest;
import com.priago.monocleapi.client.models.request.impl.SourcesRequest;
import com.priago.monocleapi.client.models.request.impl.TopHeadlinesRequest;
import com.priago.monocleapi.client.models.response.impl.GenericSearchResponse;
import com.priago.monocleapi.client.models.response.impl.SourcesResponse;
import com.priago.monocleapi.client.newsapi.MonocleNewsAPIClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.http.HttpRequest;

/**
 * Default implementation of {@link MonocleNewsAPIClient}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("monocleNewsAPIClient")
public class DefaultMonocleNewsAPIClient implements MonocleNewsAPIClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMonocleNewsAPIClient.class);
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final String BASE_NEWS_API_URL = "https://newsapi.org";
    private static final String TOP_HEADLINES_ENDPOINT = "/v2/top-headlines";
    private static final String EVERYTHING_ENDPOINT = "/v2/everything";
    private static final String SOURCES_ENDPOINT = "/v2/sources";
    private static final String EMPTY_REQUEST = "The request was invalid or empty";

    @Resource(name = "monocleClientResponseBuilder")
    private MonocleClientResponseBuilder monocleClientResponseBuilder;


    //  METHODS

    @Override
    public GenericSearchResponse fetchTopHeadlines(TopHeadlinesRequest request) {
        return performGenericHttpRequest(request, TOP_HEADLINES_ENDPOINT);
    }

    @Override
    public GenericSearchResponse fetchEverything(EverythingRequest request) {
        return performGenericHttpRequest(request, EVERYTHING_ENDPOINT);
    }

    @Override
    public SourcesResponse fetchSources(SourcesRequest request) {
        return performSourcesHttpRequest(request);
    }


    //  HELPERS

    /**
     * Generates a {@link String} of chained request parameters from the non-empty contents of the given {@link MonocleClientRequest}
     *
     * @param request a {@link MonocleClientRequest}
     * @return a string of parameters ready for an http call
     */
    private String generateParameterString(MonocleClientRequest request) {
        if (request == null || request.isEmpty() || !request.isValid()) {
            LOGGER.error(EMPTY_REQUEST);
            return StringUtils.EMPTY;
        }

        StringBuilder stringBuilder = new StringBuilder("?");
        request.getParameterMap().forEach((key, value) -> stringBuilder.append(key).append("=").append(value.toString()).append("&"));
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    /**
     * Performs a generic NewsAPI Http Request
     *
     * @param request a sub class of {@link MonocleClientRequest}
     * @param endpoint the endpoint to call for NewsAPI
     * @return a {@link GenericSearchResponse}
     */
    private GenericSearchResponse performGenericHttpRequest(MonocleClientRequest request, String endpoint) {
        if (request == null || request.isEmpty() || !request.isValid()) {
            LOGGER.error(EMPTY_REQUEST);
            return new GenericSearchResponse();
        }

        try {
            String response = sendRequest(request, endpoint);
            if (response != null) {
                LOGGER.info("Response from NewsAPI: {}", response);
                return this.monocleClientResponseBuilder.handleGenericSearchResponse(response);
            } else {
                LOGGER.error("No response from NewsAPI");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new GenericSearchResponse();
        }

        return new GenericSearchResponse();
    }

    /**
     * Performs a sources NewsAPI Http Request
     *
     * @param request a {@link SourcesRequest}
     * @return a {@link SourcesResponse}
     */
    private SourcesResponse performSourcesHttpRequest(SourcesRequest request) {
        if (request == null || request.isEmpty() || !request.isValid()) {
            LOGGER.error(EMPTY_REQUEST);
            return new SourcesResponse();
        }

        try {
            String response = sendRequest(request, SOURCES_ENDPOINT);
            if (response != null) {
                LOGGER.info("Response from NewsAPI: {}", response);
                return this.monocleClientResponseBuilder.handleSourcesResponse(response);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new SourcesResponse();
        }

        return new SourcesResponse();
    }

    /**
     * Sends an {@link HttpRequest} using an {@link HttpGet} to NewsAPI
     *
     * @param request a {@link MonocleClientRequest}
     * @param endpoint the specific NewsAPI endpoint to target
     * @return a {@link String} containing the json body response
     */
    private String sendRequest(MonocleClientRequest request, String endpoint) {
        String requestURI = BASE_NEWS_API_URL + endpoint + generateParameterString(request);
        LOGGER.info("Sending request: {}", requestURI);

        HttpGet get = new HttpGet(requestURI);
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return StringUtils.EMPTY;
        }
    }
}
