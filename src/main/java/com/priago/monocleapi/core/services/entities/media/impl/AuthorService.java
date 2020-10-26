package com.priago.monocleapi.core.services.entities.media.impl;

import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.repositories.like.author.AuthorLikeRepository;
import com.priago.monocleapi.core.repositories.media.author.AuthorRepository;
import com.priago.monocleapi.core.services.entities.media.AbstractMonocleMediaService;
import com.priago.monocleapi.core.services.entities.media.MonocleMediaService;
import com.priago.monocleapi.core.translators.media.AuthorTranslator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Default implementation of the service-layer for {@link Author} entities
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("authorService")
public class AuthorService extends AbstractMonocleMediaService<Author, AuthorLikeRepository, AuthorRepository, AuthorTranslator> implements MonocleMediaService<Author> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);

    @Resource(name = "authorRepository")
    private AuthorRepository authorRepository;


    //  METHODS

    /**
     * Attempts to find an {@link Author} by their name
     *
     * @param name author's name
     * @return an {@link Optional} if the {@link Author} is found
     */
    public Optional<Author> findAuthorByName(String name) {

        if (StringUtils.isEmpty(name)) {
            LOGGER.error("name was null or empty");
            return Optional.empty();
        }

        return this.authorRepository.findAuthorByNameContainingIgnoreCase(name);
    }
}
