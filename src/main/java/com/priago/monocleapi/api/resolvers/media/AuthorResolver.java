package com.priago.monocleapi.api.resolvers.media;

import com.priago.monocleapi.api.converters.media.AuthorConverter;
import com.priago.monocleapi.api.resolvers.MonocleResolver;
import com.priago.monocleapi.api.resources.entities.media.impl.AuthorResource;
import com.priago.monocleapi.client.models.data.impl.ArticleResponseData;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.services.entities.media.impl.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Implementation of {@link MonocleResolver} for {@link AuthorResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("authorResolver")
public class AuthorResolver implements MonocleResolver<ArticleResponseData, Author, AuthorResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorResolver.class);

    @Resource(name = "authorConverter")
    private AuthorConverter authorConverter;

    @Resource(name = "authorService")
    private AuthorService authorService;

    @Resource(name = "sourceResolver")
    private SourceResolver sourceResolver;


    //  METHODS

    @Override
    public Author resolveEntity(ArticleResponseData data, boolean save) {
        if (data == null) {
            LOGGER.error("data was null or empty");
            return null;
        }

        Optional<Author> author = this.authorService.findAuthorByName(data.getAuthor());
        if (author.isPresent()) {
            modify(author.get(), data, save);
            return save ? this.authorService.save(author.get()) : author.get();
        } else {
            Author newAuthor = new Author();
            modify(newAuthor, data, save);
            return save ? this.authorService.save(newAuthor) : newAuthor;
        }
    }

    @Override
    public AuthorResource resolveResource(ArticleResponseData data, boolean save) {
        if (data == null) {
            LOGGER.error("data was null or empty");
            return new AuthorResource();
        }

        Author author = resolveEntity(data, save);
        if (author != null) {
            return this.authorConverter.convert(author);
        }

        return null;
    }


    //  HELPERS

    /**
     * Modifies the given {@link Author} with data from the given {@link ArticleResponseData}
     *
     * @param author author to update/create
     * @param data data that was fetched
     * @param save true if the data should be saved
     */
    private void modify(final Author author, final ArticleResponseData data, boolean save) {
        if (author != null && data != null) {
            author.setCode(computedId(author, data));
            author.setName(data.getAuthor());
            author.setSource(this.sourceResolver.resolveEntity(data.getSource(), save));
        }
    }

    /**
     * Computes an id for the given source
     *
     * @param author author to update/create
     * @param data data that was fetched
     * @return a unique id
     */
    private String computedId(final Author author, final ArticleResponseData data) {
        if (StringUtils.isNotEmpty(data.getAuthor())) {
            return data.getAuthor().toLowerCase().replace(" ", "-").trim();
        } else {
            return author.getCode();
        }
    }
}
