package com.priago.monocleapi.client.models.response;

import com.priago.monocleapi.client.newsapi.MonocleNewsAPIClient;

/**
 * Parent-level skeleton for {@link MonocleNewsAPIClient} responses
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleClientResponse {

    /**
     * Returns true if the response is empty
     *
     * @return true if the required fields are empty
     */
    boolean isEmpty();

    /**
     * Returns true if the response is valid
     *
     * @return false if certain aspects of the response fail the required schema as dictated by NewsAPI
     */
    boolean isValid();

    /**
     * Returns the error code if it exists
     *
     * @return an error code will appear if the response was problematic
     */
    String getCode();

    /**
     * Returns the error message if it exists
     *
     * @return an error message will appear if the response was problematic
     */
    String getMessage();
}
