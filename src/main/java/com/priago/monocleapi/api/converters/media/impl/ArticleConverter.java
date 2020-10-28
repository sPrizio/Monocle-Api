package com.priago.monocleapi.api.converters.media.impl;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.media.MonocleMediaConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.services.entities.media.impl.ArticleService;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converter for {@link Article} entities into {@link ArticleResource} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("articleConverter")
public class ArticleConverter implements MonocleMediaConverter<Article, ArticleResource> {

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "authorConverter")
    private AuthorConverter authorConverter;

    @Resource(name = "monocleUidService")
    private MonocleUidService<Article, ArticleResource> monocleUidService;

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public ArticleResource convert(Article entity) {
        ArticleResource resource = new ArticleResource();

        if (entity != null) {
            convertCore(resource, entity);
            fetchLikes(resource, entity, false);
        }

        return resource;
    }

    @Override
    public Collection<ArticleResource> convertAll(Collection<Article> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().map(this::convert).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public ArticleResource convert(Article media, boolean excludeLikes, boolean excludeLikedBy) {
        ArticleResource resource = new ArticleResource();

        convertCore(resource, media);
        if (!excludeLikes) {
            fetchLikes(resource, media, excludeLikedBy);
        }

        return resource;
    }

    @Override
    public Collection<ArticleResource> convertAll(Collection<Article> medias, boolean excludeLikes, boolean excludeLikedBy) {
        if (CollectionUtils.isNotEmpty(medias)) {
            return medias.stream().map(med -> convert(med, excludeLikes, excludeLikedBy)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }


    //  HELPERS

    /**
     * Converts the core attributes
     *
     * @param resource {@link ArticleResource}
     * @param entity {@link Article}
     */
    private void convertCore(final ArticleResource resource, final Article entity) {
        if (entity != null) {
            resource.setUid(this.monocleUidService.computeUid(entity));
            resource.setSource(this.sourceConverter.convert(entity.getSource(), false, true));
            resource.setAuthor(this.authorConverter.convert(entity.getAuthor(), false, true));
            resource.setTitle(entity.getTitle());
            resource.setDescription(entity.getDescription());
            resource.setUrl(entity.getUrl());
            resource.setUrlToImage(entity.getUrlToImage());
            resource.setPublishedAt(entity.getPublishedAt());
            resource.setContent(entity.getContent());
        }
    }

    /**
     * Fetches likes/dislikes and liked by/disliked by
     *
     * @param resource {@link ArticleResource}
     * @param entity {@link Article}
     * @param excludeLikedBy true if liked by should not be included
     */
    private void fetchLikes(final ArticleResource resource, final Article entity, boolean excludeLikedBy) {
        if (entity != null) {
            resource.setLikes(this.articleService.getLikes(entity));
            resource.setDislikes(this.articleService.getDislikes(entity));

            if (!excludeLikedBy) {
                resource.setLikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.articleService.likedBy(entity))));
                resource.setDislikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.articleService.dislikedBy(entity))));
            }
        }
    }
}
