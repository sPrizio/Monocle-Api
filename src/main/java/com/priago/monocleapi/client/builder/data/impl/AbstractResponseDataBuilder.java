package com.priago.monocleapi.client.builder.data.impl;

import com.priago.monocleapi.client.builder.data.MonocleResponseDataBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Parent-level class for common functionality in relation to {@link MonocleResponseDataBuilder}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractResponseDataBuilder {

    /**
     * Safely gets the {@link String} value from an {@link Object} in the given {@link Map}
     *
     * @param data {@link Map}
     * @param key {@link String} key that may or may not be present
     * @return empty string if the key isn't mapped
     */
    String getMapString(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            return data.get(key).toString();
        }

        return StringUtils.EMPTY;
    }

    /**
     * Safely gets the {@link Object} in the given {@link Map}
     *
     * @param data {@link Map}
     * @param key {@link String} key that may or may not be present
     * @return null if the key isn't mapped
     */
    Object getMapObject(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }

        return null;
    }
}
