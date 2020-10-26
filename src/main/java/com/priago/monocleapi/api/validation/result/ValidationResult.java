package com.priago.monocleapi.api.validation.result;

import com.priago.monocleapi.core.enums.ValidationResponseResult;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Class implementation of a result of validation
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
@RequiredArgsConstructor
public class ValidationResult {

    @Getter
    @Setter
    @NonNull
    private ValidationResponseResult result;

    @Getter
    @Setter
    @NonNull
    private String message;


    //  METHODS

    /**
     * Determines whether the validation result is valid. We define validity as having non null parameters and the result as being successful
     *
     * @return true if the result is 'SUCCESSFUL'
     */
    public boolean isValid() {
        return (this.result != null && StringUtils.isNotEmpty(message)) && this.result.equals(ValidationResponseResult.SUCCESSFUL);
    }
}
