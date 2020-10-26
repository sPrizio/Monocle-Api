package com.priago.monocleapi.api.validation.validators;

import com.priago.monocleapi.api.validation.result.ValidationResult;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.ValidationResponseResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Collection of methods that all children will use for validation services
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractMonocleValidator {

    private static final String[] CRITICAL_WORDS = {"CREATE", "DROP", "INSERT", "TABLE", "ALTER", "DELETE", "FROM", "UPDATE"};
    private static final String[] UNACCEPTABLE_SYMBOLS = {"=", "+", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "/", "\\", "|", "[", "]", "{", "}", "~", ".", ",", ":", ";"};


    //  METHODS

    /**
     * Checks for rejected symbols
     *
     * @param s string to test
     * @return true if any symbol is detected, false otherwise
     */
    protected boolean hasUnacceptableSymbol(String s) {

        List<String> tokens = convertStringToTokens(s);

        for (String string : UNACCEPTABLE_SYMBOLS) {
            for (String token : tokens) {
                if (token.contains(string)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks for certain critical words that would be maliciously abused
     *
     * @param s string to test
     * @return true if any critical word is detected, false otherwise
     */
    protected boolean hasCriticalWord(String s) {

        List<String> tokens = convertStringToTokens(s);

        for (String string : CRITICAL_WORDS) {
            String st = string.toLowerCase();
            if (tokens.contains(st)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the string contains digits
     *
     * @param s given string
     * @return true if the string has digits
     */
    protected boolean containsDigits(String s) {
        return StringUtils.isNotEmpty(s) && s.matches(".*\\d.*");
    }

    /**
     * Checks if a string is a digit
     *
     * @param s - given string
     * @return true if string is a number, false otherwise
     */
    protected boolean isNumber(String s) {
        return NumberUtils.isCreatable(s);
    }

    /**
     * Checks if a passed integer is negative
     *
     * @param integer - passed integer
     * @return true if integer is less than 0
     */
    protected boolean isNegative(Integer integer) {
        return integer < 0;
    }

    /**
     * Checks if a passed string is too large
     *
     * @param s - passed string
     * @return true if string is of length 1001 or greater
     */
    protected boolean isTooLarge(String s) {
        return s.length() > 1000;
    }

    /**
     * Checks if a passed integer is too large
     *
     * @param integer - passed string
     * @return true if the integer is greater than 1000
     */
    protected boolean isTooLarge(Integer integer) {
        return integer > 1000;
    }

    /**
     * Checks if a passed Long is overflowing
     *
     * @param num - passed string
     * @return true if Long is overflowing
     */
    protected boolean isOverflow(Long num) {
        return num > 1000000000;
    }

    /**
     * Checks if a passed long is negative
     *
     * @param num - passed integer
     * @return true if integer is less than 0
     */
    protected boolean isNegative(Long num) {
        return num < 0;
    }

    /**
     * Checks for valid date formats according to the given format
     *
     * @param s date that we're checking
     * @param format desired date format (ex: YYYY-DD-MM)
     * @return true if the value matches the given format
     */
    protected boolean isInvalidDateForFormat(String s, String format) {

        if (isTooLarge(s) || hasCriticalWord(s)) {
            return false;
        }

        LocalDateTime dateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, MonocleCoreConstants.Core.MONOCLE_LOCALE);

        try {
            dateTime = LocalDateTime.parse(s, formatter);
            String result = dateTime.format(formatter);
            return !result.equals(s);
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(s, formatter);
                String result = date.format(formatter);
                return !result.equals(s);
            } catch (DateTimeParseException ex) {
                return true;
            }
        }
    }

    /**
     * Determines if any params are missing and if so, returns a string demarcating which ones are missing
     *
     * @param params map of request parameters
     * @param desiredParams parameters required for this request
     * @return a string containing the missing params or null if none are missing
     */
    protected String isMissingParam(Map<String, Object> params, List<String> desiredParams) {

        List<String> nonNullParams = desiredParams
                .stream()
                .filter(param -> params.get(param) != null)
                .collect(Collectors.toList());

        if (nonNullParams.size() != desiredParams.size()) {
            List<String> subtractedList = (List<String>) CollectionUtils.subtract(desiredParams, nonNullParams);
            return "Params " + subtractedList.toString() + " were not found in the request. Please include them";
        }

        return StringUtils.EMPTY;
    }

    /**
     * Validates a list of entity params using the given validator. Returns a validation result representing the status of validation
     * for the entire list
     *
     * @param listOfDetails list of game details that we need to validate
     * @param validator validator to be used when validating
     * @return validation result object
     */
    protected ValidationResult validateListOfDetails(List<Map<String, Object>> listOfDetails, MonocleValidator validator) {

        List<ValidationResult> failedValidations =
                listOfDetails
                        .stream()
                        .map(validator::validate)
                        .filter(validationResult -> !validationResult.isValid())
                        .collect(Collectors.toList());

        if (failedValidations.isEmpty()) {
            return new ValidationResult(ValidationResponseResult.SUCCESSFUL, "Validation was successful");
        }

        StringBuilder errorMessage = new StringBuilder();

        failedValidations
                .forEach(validationResult -> errorMessage.append(validationResult.getMessage()).append("\n"));

        return new ValidationResult(ValidationResponseResult.FAILED, errorMessage.toString());
    }

    /**
     * Safely gets the {@link String} value from an {@link Object} in the given {@link Map}
     *
     * @param data {@link Map}
     * @param key {@link String} key that may or may not be present
     * @return empty string if the key isn't mapped
     */
    protected String getMapString(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
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
    protected Object getMapObject(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }

        return null;
    }


    //  HELPERS

    /**
     * Converts a string to a list of its tokens
     *
     * @param string string to be broken up
     * @return list of string tokens
     */
    private List<String> convertStringToTokens(String string) {

        StringTokenizer stringTokenizer = new StringTokenizer(string);
        List<String> tokens = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(stringTokenizer.nextToken());
        }

        return tokens;
    }
}
