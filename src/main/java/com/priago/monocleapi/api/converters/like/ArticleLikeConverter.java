package com.priago.monocleapi.api.converters.like;

import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.media.impl.ArticleConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.like.impl.ArticleLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.like.impl.ArticleLike;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converter for {@link ArticleLike} entities into {@link ArticleLikeResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("articleLikeConverter")
public class ArticleLikeConverter implements MonocleConverter<ArticleLike, ArticleLikeResource> {

    @Resource(name = "articleConverter")
    private ArticleConverter articleConverter;

    @Resource(name = "monocleUidService")
    private MonocleUidService<ArticleLike, ArticleLikeResource> monocleUidService;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public ArticleLikeResource convert(ArticleLike entity) {
        ArticleLikeResource resource = new ArticleLikeResource();

        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setCardinality(MonocleLikeCardinality.valueOf(entity.getCardinality().toUpperCase()));
            resource.setDateTime(entity.getDateTime());
            resource.setResource(this.articleConverter.convert(entity.getEntity()));
            resource.setUser(this.userConverter.convert(entity.getUser()));
        }

        return resource;
    }

    @Override
    public Collection<ArticleLikeResource> convertAll(Collection<ArticleLike> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
