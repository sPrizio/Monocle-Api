package com.priago.monocleapi.api.converters.media;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.media.impl.SourceResource;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.services.entities.media.impl.SourceService;
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
public class SourceConverter implements MonocleConverter<Source, SourceResource> {

    @Resource(name = "sourceService")
    private SourceService sourceService;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public SourceResource convert(Source entity) {
        SourceResource resource = new SourceResource();

        if (entity != null) {
            resource.setId(entity.getId());
            resource.setName(entity.getName());
            resource.setDescription(entity.getDescription());
            resource.setUrl(entity.getUrl());
            resource.setCategory(EnumUtils.isValidEnumIgnoreCase(MonocleCategory.class, entity.getCategory()) ? MonocleCategory.valueOf(entity.getCategory().toUpperCase()) : MonocleCategory.NOT_APPLICABLE);
            resource.setLanguage(EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, entity.getLanguage()) ? MonocleLanguage.valueOf(entity.getLanguage().toUpperCase()) : MonocleLanguage.NOT_APPLICABLE);
            resource.setCountry(EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, entity.getCountry()) ? MonocleCountry.valueOf(entity.getCountry().toUpperCase()) : MonocleCountry.NOT_APPLICABLE);
            resource.setLikes(this.sourceService.getLikes(entity));
            resource.setDislikes(this.sourceService.getDislikes(entity));
            resource.setLikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.sourceService.likedBy(entity))));
            resource.setDislikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.sourceService.dislikedBy(entity))));
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
}
