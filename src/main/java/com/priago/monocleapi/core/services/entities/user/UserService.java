package com.priago.monocleapi.core.services.entities.user;

import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.repositories.user.UserRepository;
import com.priago.monocleapi.core.services.entities.AbstractMonocleEntityService;
import com.priago.monocleapi.core.services.entities.MonocleEntityService;
import com.priago.monocleapi.core.translators.user.UserTranslator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 *  Default implementation of the service-layer for {@link User} entities
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("userService")
public class UserService extends AbstractMonocleEntityService<User, UserRepository, UserTranslator> implements MonocleEntityService<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Resource(name = "userRepository")
    private UserRepository userRepository;


    //  METHODS

    /**
     * Finds a {@link User} for the given email
     *
     * @param email email to look up
     * @return {@link Optional} {@link User}
     */
    public Optional<User> findUserByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            LOGGER.error("No user found for email {}", email);
            return Optional.empty();
        }

        return this.userRepository.findUserByEmail(email);
    }
}
