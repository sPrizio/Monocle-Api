package com.priago.monocleapi.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A standard response in JSON that is returned by the API endpoints
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class StandardJsonResponse {

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private Object responseObject;

    @Getter
    @Setter
    private String message;


    /**
     * Generic constructor
     *
     * @param success boolean whether this response was successful for its query
     * @param responseObject data to return
     * @param message message to display
     */
    public StandardJsonResponse(boolean success, Object responseObject, String message) {
        this.success = success;
        this.responseObject = responseObject;
        this.message = message;
    }
}
