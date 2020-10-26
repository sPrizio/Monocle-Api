package com.priago.monocleapi.api.converters.media;

import com.google.common.collect.Sets;
import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.converters.user.UserConverter;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.services.entities.media.impl.ArticleService;
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
public class ArticleConverter implements MonocleConverter<Article, ArticleResource> {

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "authorConverter")
    private AuthorConverter authorConverter;

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "userConverter")
    private UserConverter userConverter;


    //  METHODS

    @Override
    public ArticleResource convert(Article entity) {
        ArticleResource resource = new ArticleResource();

        if (entity != null) {
            resource.setSource(this.sourceConverter.convert(entity.getSource()));
            resource.setAuthor(this.authorConverter.convert(entity.getAuthor()));
            resource.setTitle(entity.getTitle());
            resource.setDescription(entity.getDescription());
            resource.setUrl(entity.getUrl());
            resource.setUrlToImage(entity.getUrlToImage());
            resource.setPublishedAt(entity.getPublishedAt());
            resource.setContent(entity.getContent());
            resource.setLikes(this.articleService.getLikes(entity));
            resource.setDislikes(this.articleService.getDislikes(entity));
            resource.setLikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.articleService.likedBy(entity))));
            resource.setDislikedBy(Sets.newLinkedHashSet(this.userConverter.convertAllBasic(this.articleService.dislikedBy(entity))));
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
}
