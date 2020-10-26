package com.priago.monocleapi.core.exceptions;

/**
 * An exception for entities that could not be found in Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class MonocleEntityNotFoundException extends Exception {

    /**
     * Inheriting constructor
     *
     * @param message exception message
     */
    public MonocleEntityNotFoundException(String message) {
        super(message);
    }
}
