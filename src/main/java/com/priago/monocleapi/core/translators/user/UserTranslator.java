package com.priago.monocleapi.core.translators.user;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.translators.AbstractMonocleTranslator;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Translates a map into a {@link User} entity
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("userTranslator")
public class UserTranslator extends AbstractMonocleTranslator implements MonocleTranslator<User> {


    //  METHODS

    @Override
    public User translate(Map<String, Object> values) {
        if (MapUtils.isEmpty(values)) {
            return null;
        }

        User user = new User();

        user.setUsername(getMapString(values, MonocleCoreConstants.Security.USERNAME));
        user.setFirstName(getMapString(values, MonocleCoreConstants.Security.FIRST_NAME));
        user.setLastName(getMapString(values, MonocleCoreConstants.Security.LAST_NAME));
        user.setEmail(getMapString(values, MonocleCoreConstants.Security.EMAIL));
        user.setPassword(getMapString(values, MonocleCoreConstants.Security.PASSWORD));
        user.setActive(Boolean.parseBoolean(getMapString(values, MonocleCoreConstants.Security.ACTIVE)));

        return user;
    }
}
