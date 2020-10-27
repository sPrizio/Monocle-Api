package com.priago.monocleapi.api.converters.like;

import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.media.AuthorConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.like.impl.AuthorLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.like.impl.AuthorLike;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converter for {@link AuthorLike} entities into {@link AuthorLikeResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("authorLikeConverter")
public class AuthorLikeConverter implements MonocleConverter<AuthorLike, AuthorLikeResource> {

    @Resource(name = "authorConverter")
    private AuthorConverter authorConverter;

    @Resource(name = "monocleUidService")
    private MonocleUidService<AuthorLike, AuthorLikeResource> monocleUidService;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public AuthorLikeResource convert(AuthorLike entity) {
        AuthorLikeResource resource = new AuthorLikeResource();

        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setCardinality(MonocleLikeCardinality.valueOf(entity.getCardinality().toUpperCase()));
            resource.setDateTime(entity.getDateTime());
            resource.setResource(this.authorConverter.convert(entity.getEntity()));
            resource.setUser(this.userConverter.convert(entity.getUser()));
        }

        return resource;
    }

    @Override
    public Collection<AuthorLikeResource> convertAll(Collection<AuthorLike> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
