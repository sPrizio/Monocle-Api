package com.priago.monocleapi.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Arrays;
import java.util.Objects;

/**
 * Provides general utility methods
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class MonocleUtils {

    private MonocleUtils() {
        throw new UnsupportedOperationException("Utility classes should not be instantiated");
    }


    //  METHODS

    /**
     * Validates that all given objects are non-null
     *
     * @param objects list of objects to validate
     * @return true if every given object is not null
     */
    public static boolean areNonNull(Object... objects) {
        return Arrays.stream(objects).noneMatch(Objects::isNull);
    }

    /**
     * Returns true if the given {@link String} is a valid url
     *
     * @param url string url
     * @return false if not a valid url
     */
    public static boolean isValidUrl(String url) {
        if (StringUtils.isNotEmpty(url)) {
            return UrlValidator.getInstance().isValid(url);
        }

        return false;
    }
}
