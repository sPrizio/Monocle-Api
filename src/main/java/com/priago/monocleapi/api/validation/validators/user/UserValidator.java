package com.priago.monocleapi.api.validation.validators.user;

import com.priago.monocleapi.api.validation.result.ValidationResult;
import com.priago.monocleapi.api.validation.validators.AbstractMonocleValidator;
import com.priago.monocleapi.api.validation.validators.MonocleValidator;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.ValidationResponseResult;
import com.priago.monocleapi.core.models.entities.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link MonocleValidator} for {@link User} entities
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("userValidator")
public class UserValidator extends AbstractMonocleValidator implements MonocleValidator {

    private static final List<String> EXPECTED_PARAMS = Arrays.asList("firstName", "lastName", "email", "password", "confirmPassword");


    //  METHODS

    @Override
    public ValidationResult validate(Map<String, Object> values) {

        //  check for missing params
        if (StringUtils.isNotEmpty(super.isMissingParam(values, EXPECTED_PARAMS))) {
            return new ValidationResult(ValidationResponseResult.FAILED, super.isMissingParam(values, EXPECTED_PARAMS));
        }

        String firstName = getMapString(values, MonocleCoreConstants.Security.FIRST_NAME);
        String lastName = getMapString(values, MonocleCoreConstants.Security.LAST_NAME);
        String email = getMapString(values, MonocleCoreConstants.Security.EMAIL);
        String password = getMapString(values, MonocleCoreConstants.Security.PASSWORD);// TODO: might need to decode
        String confirmPassword = getMapString(values, MonocleCoreConstants.Security.CONFIRM_PASSWORD);// TODO: might need to decode

        if (super.isTooLarge(firstName) || super.containsDigits(firstName)) {
            return new ValidationResult(ValidationResponseResult.FAILED, "firstName was too large or contained digits");
        }

        if (super.isTooLarge(lastName) || super.containsDigits(lastName)) {
            return new ValidationResult(ValidationResponseResult.FAILED, "lastName was too large or contained digits");
        }

        if (super.isTooLarge(email)) {
            return new ValidationResult(ValidationResponseResult.FAILED, "email was too large");
        }

        if (super.isTooLarge(password)) {
            return new ValidationResult(ValidationResponseResult.FAILED, "password was too large");
        }

        if (!password.equals(confirmPassword)) {
            return new ValidationResult(ValidationResponseResult.FAILED, "passwords don't match");
        }

        return new ValidationResult(ValidationResponseResult.SUCCESSFUL, "Validation was successful");
    }
}
