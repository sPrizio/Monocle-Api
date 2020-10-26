package com.priago.monocleapi.core.services.entities.media.impl;

import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.repositories.like.article.ArticleLikeRepository;
import com.priago.monocleapi.core.repositories.media.article.ArticleRepository;
import com.priago.monocleapi.core.services.entities.media.AbstractMonocleMediaService;
import com.priago.monocleapi.core.services.entities.media.MonocleMediaService;
import com.priago.monocleapi.core.translators.media.ArticleTranslator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the service-layer for {@link Article} entities
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("articleService")
public class ArticleService extends AbstractMonocleMediaService<Article, ArticleLikeRepository, ArticleRepository, ArticleTranslator> implements MonocleMediaService<Article> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    @Resource(name = "articleRepository")
    private ArticleRepository articleRepository;


    //  METHODS

    /**
     * Finds a {@link List} of {@link Article}s by their {@link Author}
     *
     * @param author article's author
     * @return a {@link List} of {@link Article}s with the given {@link Author}
     */
    public List<Article> findArticlesByAuthor(Author author, Pageable pageable) {
        if (author == null) {
            LOGGER.error("author was null or empty");
            return Collections.emptyList();
        }

        return this.articleRepository.findArticlesByAuthor(author, pageable);
    }

    /**
     * Finds a {@link List} of {@link Article}s by their {@link Source}
     *
     * @param source article's source
     * @return a {@link List} of {@link Article}s with the given {@link Source}
     */
    public List<Article> findArticlesBySource(Source source, Pageable pageable) {
        if (source == null) {
            LOGGER.error("source was null or empty");
            return Collections.emptyList();
        }

        return this.articleRepository.findArticlesBySource(source, pageable);
    }

    /**
     * Finds an {@link Article} by its url
     *
     * @param url article url
     * @return an {@link Optional} containing an {@link Article}
     */
    public Optional<Article> findArticleByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("url was null or empty");
            return Optional.empty();
        }

        return this.articleRepository.findArticleByUrl(url);
    }

    /**
     * Finds an {@link Article} by its title
     *
     * @param title article title
     * @return a {@link List} containing {@link Article}s that best match the given title
     */
    public List<Article> findArticleByTitle(String title, Pageable pageable) {
        if (StringUtils.isEmpty(title)) {
            LOGGER.error("title was null or empty");
            return Collections.emptyList();
        }

        return this.articleRepository.findArticlesByTitleContainingIgnoreCase(title, pageable);
    }
}
