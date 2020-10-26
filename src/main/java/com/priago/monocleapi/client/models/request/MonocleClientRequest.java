package com.priago.monocleapi.client.models.request;

import com.priago.monocleapi.client.newsapi.MonocleNewsAPIClient;

import java.util.Map;

/**
 * Parent-level skeleton for {@link MonocleNewsAPIClient} requests
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleClientRequest {

    /**
     * Returns true if the request is empty
     *
     * @return true if the required fields are empty
     */
    boolean isEmpty();

    /**
     * Returns true if the request is valid
     *
     * @return false if certain aspects of the request fail the required schema as dictated by NewsAPI
     */
    boolean isValid();

    /**
     * Returns a {@link Map} of the non-empty request attributes
     *
     * @return a map of attribute keys and their values
     */
    Map<String, Object> getParameterMap();
}
