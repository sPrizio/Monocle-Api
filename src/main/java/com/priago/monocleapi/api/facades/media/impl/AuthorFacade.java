package com.priago.monocleapi.api.facades.media.impl;

import com.priago.monocleapi.api.converters.media.impl.AuthorConverter;
import com.priago.monocleapi.api.facades.media.AbstractMonocleMediaFacade;
import com.priago.monocleapi.api.facades.media.MonocleMediaFacade;
import com.priago.monocleapi.api.resources.entities.media.impl.AuthorResource;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.services.entities.media.impl.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Facade implementation for {@link AuthorResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("authorFacade")
public class AuthorFacade extends AbstractMonocleMediaFacade<Author, AuthorResource, AuthorConverter, AuthorService> implements MonocleMediaFacade<AuthorResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorFacade.class);

    @Resource(name = "authorConverter")
    private AuthorConverter authorConverter;

    @Resource(name = "authorService")
    private AuthorService authorService;


    //  METHODS

    /**
     * Find an {@link AuthorResource} by their name
     *
     * @param name name of the author
     * @return a {@link AuthorResource}
     */
    public AuthorResource findAuthorByName(String name) {
        if (StringUtils.isEmpty(name)) {
            LOGGER.error("name was null or empty");
            return new AuthorResource();
        }

        Optional<Author> author = this.authorService.findAuthorByName(name);
        if (author.isPresent()) {
            return this.authorConverter.convert(author.get());
        }

        LOGGER.error("No author found for name {}", name);
        return new AuthorResource();
    }
}
