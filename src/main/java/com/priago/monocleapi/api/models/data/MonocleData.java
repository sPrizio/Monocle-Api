package com.priago.monocleapi.api.models.data;

/**
 * A data object is a non-entity non-database managed object designed exclusively for front-end usage
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleData {

    /**
     * Returns true if the data object is missing its key components
     *
     * @return false if the object is valid
     */
    boolean isEmpty();
}
