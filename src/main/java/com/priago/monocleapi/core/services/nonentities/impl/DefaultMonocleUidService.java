package com.priago.monocleapi.core.services.nonentities.impl;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import com.priago.monocleapi.core.util.MonocleNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Default implementation of {@link MonocleUidService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("monocleUidService")
public class DefaultMonocleUidService<E extends MonocleEntity, R extends MonocleResource> implements MonocleUidService<E, R> {

    private static final long MULTIPLICAND = -245895L;
    private static final int OFFSET = -91;
    private static final String CHAR_DELIMITER = "e";
    private static final String DELIMITER = "@";


    //  METHODS

    @Override
    public String computeUid(E entity) {
        if (entity == null) {
            return StringUtils.EMPTY;
        }

        String name = entity.getClass().getSimpleName();
        String arr = convertIntArrayToString(convertToIntArray(name.getBytes(StandardCharsets.US_ASCII)));
        String multiplied = String.valueOf(entity.getPk() * MULTIPLICAND);

        return arr + DELIMITER + multiplied;
    }

    @Override
    public Long computePk(R resource) {
        if (resource.isPresent()) {
            String[] array = resource.getUid().split(DELIMITER);
            if (array.length > 1) {
                return MonocleNumberUtils.safeParseLong(array[1]) / MULTIPLICAND;
            }
        }

        return -1L;
    }

    @Override
    public String compute(R resource) {
        if (!resource.isPresent()) {
            return StringUtils.EMPTY;
        }

        String[] array = resource.getUid().split(DELIMITER);
        if (array.length > 0) {
            String[] chars = array[0].split(CHAR_DELIMITER);
            int[] unmasked = convertToIntArray(chars);
            byte[] bytes = new byte[unmasked.length];

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) unmasked[i];
            }

            String clazz = new String(bytes, StandardCharsets.UTF_8);
            if (array.length > 1) {
                long pk = Long.parseLong(array[1]) / MULTIPLICAND;
                return clazz + " " + pk;
            }
        }

        return StringUtils.EMPTY;
    }


    //  HELPERS

    /**
     * Converts a byte array to an integer array
     *
     * @param input array of bytes
     * @return array of integers
     */
    private int[] convertToIntArray(byte[] input) {
        int[] ret = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            ret[i] = (input[i] & 0xff) * OFFSET;
        }

        return ret;
    }

    /**
     * Converts a String array to an integer array
     *
     * @param input arrays of {@link String}s
     * @return array of integers
     */
    private int[] convertToIntArray(String[] input) {
        int[] ret = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            ret[i] = MonocleNumberUtils.safeParseInteger(input[i]) / OFFSET;
        }

        return ret;
    }

    /**
     * Converts an integer array to a string
     *
     * @param array array of integers
     * @return {@link String}
     */
    private String convertIntArrayToString(int[] array) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(array).forEach(i -> builder.append(i).append(CHAR_DELIMITER));
        return builder.toString();
    }
}
