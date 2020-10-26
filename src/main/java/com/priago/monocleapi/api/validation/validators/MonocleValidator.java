package com.priago.monocleapi.api.validation.validators;

import com.priago.monocleapi.api.validation.result.ValidationResult;

import java.util.Map;

/**
 * Validation blueprint for all validators
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleValidator {

    /**
     * Validates a map of parameters used for entity operations. We return a validation result as a consequence of computation
     *
     * @param values insertion params
     * @return a validation result based on the state of the insertion params
     */
    ValidationResult validate(Map<String, Object> values);
}
