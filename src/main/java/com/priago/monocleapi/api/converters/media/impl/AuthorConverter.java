package com.priago.monocleapi.api.converters.media.impl;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.media.MonocleMediaConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.media.impl.AuthorResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.services.entities.media.impl.AuthorService;
import com.priago.monocleapi.core.services.nonentities.MonocleLikeCacheService;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class AuthorConverter implements MonocleMediaConverter<Author, AuthorResource> {

    @Resource(name = "authorService")
    private AuthorService authorService;

    @Resource(name = "monocleLikeCacheService")
    private MonocleLikeCacheService monocleLikeCacheService;

    @Resource(name = "monocleUidService")
    private MonocleUidService<Author, AuthorResource> monocleUidService;

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public AuthorResource convert(Author entity) {
        AuthorResource resource = new AuthorResource();

        if (entity != null) {
            convertCore(resource, entity);
            fetchLikes(resource, entity, false);
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

    @Override
    public AuthorResource convert(Author media, boolean excludeLikes, boolean excludeLikedBy) {
        AuthorResource resource = new AuthorResource();

        convertCore(resource, media);
        if (!excludeLikes) {
            fetchLikes(resource, media, excludeLikedBy);
        }

        return resource;
    }

    @Override
    public Collection<AuthorResource> convertAll(Collection<Author> medias, boolean excludeLikes, boolean excludeLikedBy) {
        if (CollectionUtils.isNotEmpty(medias)) {
            return medias.stream().map(med -> convert(med, excludeLikes, excludeLikedBy)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }


    //  HELPERS

    /**
     * Converts the core attributes
     *
     * @param resource {@link AuthorResource}
     * @param entity {@link Author}
     */
    private void convertCore(final AuthorResource resource, final Author entity) {
        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setSource(this.sourceConverter.convert(entity.getSource(), false, true));
            resource.setCode(entity.getCode());
            resource.setName(entity.getName());
        }
    }

    /**
     * Fetches likes/dislikes and liked by/disliked by
     *
     * @param resource {@link AuthorResource}
     * @param entity {@link Author}
     * @param excludeLikedBy true if liked by should not be included
     */
    private void fetchLikes(final AuthorResource resource, final Author entity, boolean excludeLikedBy) {
        if (entity != null) {
            resource.setLikes(BigDecimal.valueOf(this.authorService.getLikes(entity)).add(BigDecimal.valueOf(this.monocleLikeCacheService.getCount(resource.getUid(), MonocleLikeCardinality.LIKE))).intValue());
            resource.setDislikes(BigDecimal.valueOf(this.authorService.getDislikes(entity)).add(BigDecimal.valueOf(this.monocleLikeCacheService.getCount(resource.getUid(), MonocleLikeCardinality.DISLIKE))).intValue());

            if (!excludeLikedBy) {
                resource.setLikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.authorService.likedBy(entity))));
                resource.setDislikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.authorService.dislikedBy(entity))));
            }
        }
    }
}
