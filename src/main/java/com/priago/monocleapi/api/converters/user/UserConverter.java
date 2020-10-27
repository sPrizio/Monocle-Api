package com.priago.monocleapi.api.converters.user;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.media.ArticleConverter;
import com.priago.monocleapi.api.converters.media.AuthorConverter;
import com.priago.monocleapi.api.converters.media.SourceConverter;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.services.entities.media.impl.ArticleService;
import com.priago.monocleapi.core.services.entities.media.impl.AuthorService;
import com.priago.monocleapi.core.services.entities.media.impl.SourceService;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Converter for {@link User} entities into {@link UserResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("userConverter")
public class UserConverter implements MonocleConverter<User, UserResource> {

    @Resource(name = "articleConverter")
    private ArticleConverter articleConverter;

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "authorConverter")
    private AuthorConverter authorConverter;

    @Resource(name = "authorService")
    private AuthorService authorService;

    @Resource(name = "monocleUidService")
    private MonocleUidService<User, UserResource> monocleUidService;

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "sourceService")
    private SourceService sourceService;


    //  METHODS

    /**
     * Populates only the basic attributes from the given {@link User}
     *
     * @param entity {@link User}
     * @return minimized {@link UserResource}
     */
    public UserResource convertBasic(User entity) {
        UserResource resource = new UserResource();

        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setUsername(entity.getUsername());
            resource.setFirstName(StringUtils.EMPTY);
            resource.setLastName(StringUtils.EMPTY);
            resource.setEmail(StringUtils.EMPTY);
            resource.setActive(true);
            resource.setLikedArticles(new HashSet<>());
            resource.setDislikedArticles(new HashSet<>());
            resource.setLikedAuthors(new HashSet<>());
            resource.setDislikedAuthors(new HashSet<>());
            resource.setLikedSources(new HashSet<>());
            resource.setDislikedSources(new HashSet<>());
        }

        return resource;
    }

    /**
     * Populates all entities with only the basic information
     *
     * @param entities collection of {@link User}s
     * @return collection of converted {@link UserResource}s
     */
    public Collection<UserResource> convertAllBasic(Collection<User> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convertBasic).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public UserResource convert(User entity) {
        UserResource resource = new UserResource();

        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setUsername(entity.getUsername());
            resource.setFirstName(entity.getFirstName());
            resource.setLastName(entity.getLastName());
            resource.setEmail(entity.getEmail());
            resource.setActive(entity.isActive());
            resource.setLikedArticles(Sets.newLinkedHashSet(this.articleConverter.convertAll(this.articleService.liked(entity))));
            resource.setDislikedArticles(Sets.newLinkedHashSet(this.articleConverter.convertAll(this.articleService.disliked(entity))));
            resource.setLikedAuthors(Sets.newLinkedHashSet(this.authorConverter.convertAll(this.authorService.liked(entity))));
            resource.setDislikedAuthors(Sets.newLinkedHashSet(this.authorConverter.convertAll(this.authorService.disliked(entity))));
            resource.setLikedSources(Sets.newLinkedHashSet(this.sourceConverter.convertAll(this.sourceService.liked(entity))));
            resource.setDislikedSources(Sets.newLinkedHashSet(this.sourceConverter.convertAll(this.sourceService.disliked(entity))));
        }

        return resource;
    }

    @Override
    public Collection<UserResource> convertAll(Collection<User> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
