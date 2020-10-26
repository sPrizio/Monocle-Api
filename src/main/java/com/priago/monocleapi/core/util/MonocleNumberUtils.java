package com.priago.monocleapi.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides number utility methods
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class MonocleNumberUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonocleNumberUtils.class);

    private MonocleNumberUtils() {
        throw new UnsupportedOperationException("Utility classes should not be instantiated");
    }


    //  METHODS

    /**
     * Returns true if the given {@link String} is a number
     *
     * @param string string to test
     * @return false if this is not a valid {@link Number}
     */
    public static boolean isNumber(String string) {
        if (StringUtils.isNotEmpty(string)) {
            return NumberUtils.isCreatable(string);
        }

        return false;
    }

    /**
     * Safely parses a {@link String} into an {@link Integer}
     *
     * @param s integer as a string
     * @return -1 if the string was not a valid integer
     */
    public static int safeParseInteger(String s) {
        if (StringUtils.isNotEmpty(s)) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return -1;
            }
        }

        return -1;
    }

    /**
     * Attempts to parse a string into a {@link Long}
     *
     * @param string long string to be parsed
     * @return will return null if the string cannot be parsed
     */
    public static Long safeParseLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (Exception e) {
            LOGGER.error("Long could not be parsed from the given string {}", string);
            return null;
        }
    }
}
