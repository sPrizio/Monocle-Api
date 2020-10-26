package com.priago.monocleapi.api.facades.user;

import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.facades.AbstractMonocleEntityFacade;
import com.priago.monocleapi.api.facades.MonocleEntityFacade;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.services.entities.user.UserService;
import org.springframework.stereotype.Component;

/**
 * Facade implementation for {@link UserResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("userFacade")
public class UserFacade extends AbstractMonocleEntityFacade<User, UserResource, UserConverter, UserService> implements MonocleEntityFacade<UserResource> {
}
