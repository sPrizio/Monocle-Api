package com.priago.monocleapi.api.resolvers.media;

import com.priago.monocleapi.api.converters.media.impl.ArticleConverter;
import com.priago.monocleapi.api.resolvers.MonocleResolver;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.client.models.data.impl.ArticleResponseData;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.services.entities.media.impl.ArticleService;
import com.priago.monocleapi.core.services.nonentities.DateTimeService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Implementation of {@link MonocleResolver} for {@link ArticleResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("articleResolver")
public class ArticleResolver implements MonocleResolver<ArticleResponseData, Article, ArticleResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleResolver.class);

    @Resource(name = "articleConverter")
    private ArticleConverter articleConverter;

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "authorResolver")
    private AuthorResolver authorResolver;

    @Resource(name = "dateTimeService")
    private DateTimeService dateTimeService;

    @Resource(name = "sourceResolver")
    private SourceResolver sourceResolver;


    //  METHODS

    @Override
    public Article resolveEntity(ArticleResponseData data, boolean save) {

        //  only use url since if multiple articles are searched Monocle will only
        //  apply database updates when a single one is selected, for performance reasons
        if (data == null) {
            LOGGER.error("data was null or empty");
            return null;
        }

        if (StringUtils.isEmpty(data.getUrl())) {
            LOGGER.error("article must have a url");
            return null;
        }

        Optional<Article> article = this.articleService.findArticleByUrl(data.getUrl());
        if (article.isPresent()) {
            modify(article.get(), data, save);
            return save ? this.articleService.save(article.get()) : article.get();
        } else {
            Article newArticle = new Article();
            modify(newArticle, data, save);
            return save ? this.articleService.save(newArticle) : newArticle;
        }
    }

    @Override
    public ArticleResource resolveResource(ArticleResponseData data, boolean save) {
        if (data == null) {
            LOGGER.error("data was null or empty");
            return new ArticleResource();
        }

        Article article = resolveEntity(data, save);
        if (article != null) {
            return this.articleConverter.convert(article);
        }

        return null;
    }


    //  HELPERS

    /**
     * Modifies the given {@link Article} with data from the given {@link ArticleResponseData}
     *
     * @param article article to update/create
     * @param data data that was fetched
     * @param save true if the data should be saved
     */
    private void modify(final Article article, final ArticleResponseData data, boolean save) {
        article.setContent(StringUtils.isNotEmpty(data.getContent()) ? Jsoup.parse(data.getContent()).text() : article.getContent());
        article.setPublishedAt(StringUtils.isNotEmpty(data.getPublishedAt()) ? this.dateTimeService.parseDateTimeStringForFormat(data.getPublishedAt(), DateTimeFormatter.ofPattern(MonocleCoreConstants.Core.ISO_DATE_TIME_FORMAT)) : article.getPublishedAt());
        article.setUrlToImage(StringUtils.isNotEmpty(data.getUrlToImage()) ? data.getUrlToImage() : article.getUrlToImage());
        article.setUrl(StringUtils.isNotEmpty(data.getUrl()) ? data.getUrl() : article.getUrl());
        article.setDescription(StringUtils.isNotEmpty(data.getDescription()) ? Jsoup.parse(data.getDescription()).text() : article.getDescription());
        article.setTitle(StringUtils.isNotEmpty(data.getTitle()) ? Jsoup.parse(data.getTitle()).text() :  article.getTitle());
        article.setAuthor(this.authorResolver.resolveEntity(data, save));
        article.setSource(this.sourceResolver.resolveEntity(data.getSource(), save));
    }
}
