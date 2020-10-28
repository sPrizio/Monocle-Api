package com.priago.monocleapi.api.converters.media.impl;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.media.MonocleMediaConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.media.impl.SourceResource;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.services.entities.media.impl.SourceService;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converter for {@link Source} entities into {@link SourceResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceConverter")
public class SourceConverter implements MonocleMediaConverter<Source, SourceResource> {

    @Resource(name = "monocleUidService")
    private MonocleUidService<Source, SourceResource> monocleUidService;

    @Resource(name = "sourceService")
    private SourceService sourceService;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public SourceResource convert(Source entity) {
        SourceResource resource = new SourceResource();

        if (entity != null) {
            convertCore(resource, entity);
            fetchLikes(resource, entity, false);
        }

        return resource;
    }

    @Override
    public Collection<SourceResource> convertAll(Collection<Source> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public SourceResource convert(Source media, boolean excludeLikes, boolean excludeLikedBy) {
        SourceResource resource = new SourceResource();

        convertCore(resource, media);
        if (!excludeLikes) {
            fetchLikes(resource, media, excludeLikedBy);
        }

        return resource;
    }

    @Override
    public Collection<SourceResource> convertAll(Collection<Source> medias, boolean excludeLikes, boolean excludeLikedBy) {
        if (CollectionUtils.isNotEmpty(medias)) {
            return medias.stream().map(med -> convert(med, excludeLikes, excludeLikedBy)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }


    //  HELPERS

    /**
     * Converts the core attributes
     *
     * @param resource {@link SourceResource}
     * @param entity {@link Source}
     */
    private void convertCore(final SourceResource resource, final Source entity) {
        resource.setUid(this.monocleUidService.computeUid(entity));
        resource.setId(entity.getId());
        resource.setName(entity.getName());
        resource.setDescription(entity.getDescription());
        resource.setUrl(entity.getUrl());
        resource.setCategory(EnumUtils.isValidEnumIgnoreCase(MonocleCategory.class, entity.getCategory()) ? MonocleCategory.valueOf(entity.getCategory().toUpperCase()) : MonocleCategory.NOT_APPLICABLE);
        resource.setLanguage(EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, entity.getLanguage()) ? MonocleLanguage.valueOf(entity.getLanguage().toUpperCase()) : MonocleLanguage.NOT_APPLICABLE);
        resource.setCountry(EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, entity.getCountry()) ? MonocleCountry.valueOf(entity.getCountry().toUpperCase()) : MonocleCountry.NOT_APPLICABLE);
    }

    /**
     * Fetches likes/dislikes and liked by/disliked by
     *
     * @param resource {@link SourceResource}
     * @param entity {@link Source}
     * @param excludeLikedBy true if liked by should not be included
     */
    private void fetchLikes(final SourceResource resource, final Source entity, boolean excludeLikedBy) {
        resource.setLikes(this.sourceService.getLikes(entity));
        resource.setDislikes(this.sourceService.getDislikes(entity));

        if (!excludeLikedBy) {
            resource.setLikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.sourceService.likedBy(entity))));
            resource.setDislikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.sourceService.dislikedBy(entity))));
        }
    }
}
