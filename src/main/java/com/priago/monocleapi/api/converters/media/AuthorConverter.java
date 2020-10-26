package com.priago.monocleapi.api.converters.media;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.media.impl.AuthorResource;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.services.entities.media.impl.AuthorService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converter for {@link Author} entities into {@link AuthorResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("authorConverter")
public class AuthorConverter implements MonocleConverter<Author, AuthorResource> {

    @Resource(name = "authorService")
    private AuthorService authorService;

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public AuthorResource convert(Author entity) {
        AuthorResource resource = new AuthorResource();

        if (entity != null) {
            resource.setSource(this.sourceConverter.convert(entity.getSource()));
            resource.setCode(entity.getCode());
            resource.setName(entity.getName());
            resource.setLikes(this.authorService.getLikes(entity));
            resource.setDislikes(this.authorService.getDislikes(entity));
            resource.setLikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.authorService.likedBy(entity))));
            resource.setDislikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.authorService.dislikedBy(entity))));
        }

        return resource;
    }

    @Override
    public Collection<AuthorResource> convertAll(Collection<Author> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
