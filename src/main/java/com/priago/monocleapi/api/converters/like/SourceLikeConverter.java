package com.priago.monocleapi.api.converters.like;

import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.media.SourceConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.like.impl.SourceLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.like.impl.SourceLike;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converter for {@link SourceLike} entities into {@link SourceLikeResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceLikeConverter")
public class SourceLikeConverter implements MonocleConverter<SourceLike, SourceLikeResource> {

    @Resource(name = "monocleUidService")
    private MonocleUidService<SourceLike, SourceLikeResource> monocleUidService;

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public SourceLikeResource convert(SourceLike entity) {
        SourceLikeResource resource = new SourceLikeResource();

        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setCardinality(MonocleLikeCardinality.valueOf(entity.getCardinality().toUpperCase()));
            resource.setDateTime(entity.getDateTime());
            resource.setResource(this.sourceConverter.convert(entity.getEntity()));
            resource.setUser(this.userConverter.convert(entity.getUser()));
        }

        return resource;
    }

    @Override
    public Collection<SourceLikeResource> convertAll(Collection<SourceLike> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
