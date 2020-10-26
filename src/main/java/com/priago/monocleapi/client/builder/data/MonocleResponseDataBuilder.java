package com.priago.monocleapi.client.builder.data;

import com.priago.monocleapi.client.models.data.MonocleResponseData;

import java.net.http.HttpResponse;
import java.util.Map;

/**
 * Defines the structure for building a {@link MonocleResponseData} object
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleResponseDataBuilder<D extends MonocleResponseData> {

    /**
     * Builds the {@link MonocleResponseData} from the given {@link HttpResponse}.
     * NOTE: the response is expected to be a json string
     *
     * @param data a json payload in the form of a {@link Map}
     * @return a {@link MonocleResponseData}
     */
    D buildResponseData(Map<String, Object> data);
}
