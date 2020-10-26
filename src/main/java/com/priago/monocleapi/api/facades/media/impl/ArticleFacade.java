package com.priago.monocleapi.api.facades.media.impl;

import com.google.common.collect.Lists;
import com.priago.monocleapi.api.converters.media.ArticleConverter;
import com.priago.monocleapi.api.facades.media.AbstractMonocleMediaFacade;
import com.priago.monocleapi.api.facades.media.MonocleMediaFacade;
import com.priago.monocleapi.api.models.data.search.MonoclePageAndSortData;
import com.priago.monocleapi.api.resolvers.media.ArticleResolver;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.client.builder.request.MonocleClientRequestBuilder;
import com.priago.monocleapi.client.models.data.impl.ArticleResponseData;
import com.priago.monocleapi.client.models.response.impl.GenericSearchResponse;
import com.priago.monocleapi.client.newsapi.MonocleNewsAPIClient;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleClientSort;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.services.entities.media.impl.ArticleService;
import com.priago.monocleapi.core.services.entities.media.impl.AuthorService;
import com.priago.monocleapi.core.services.entities.media.impl.SourceService;
import com.priago.monocleapi.core.util.MonocleNumberUtils;
import com.priago.monocleapi.core.util.MonocleUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Facade implementation for {@link ArticleResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("articleFacade")
public class ArticleFacade extends AbstractMonocleMediaFacade<Article, ArticleResource, ArticleConverter, ArticleService> implements MonocleMediaFacade<ArticleResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleFacade.class);

    @Resource(name = "articleConverter")
    private ArticleConverter articleConverter;

    @Resource(name = "articleResolver")
    private ArticleResolver articleResolver;

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "authorService")
    private AuthorService authorService;

    @Resource(name = "monocleClientRequestBuilder")
    private MonocleClientRequestBuilder monocleClientRequestBuilder;

    @Resource(name = "monocleNewsAPIClient")
    private MonocleNewsAPIClient monocleNewsAPIClient;

    @Resource(name = "sourceService")
    private SourceService sourceService;


    //  METHODS

    /**
     * Finds a {@link List} of {@link Article}s by their {@link Author}
     *
     * @param authorQ         a string that may contain an author's code or name
     * @param pageAndSortData pagination and sort data
     * @return a {@link List} of {@link Article}s with the given {@link Author}
     */
    public List<ArticleResource> findArticlesByAuthor(String authorQ, MonoclePageAndSortData pageAndSortData) {
        if (StringUtils.isEmpty(authorQ)) {
            LOGGER.error("author was null or empty");
            return Collections.emptyList();
        }

        Optional<Author> author = MonocleNumberUtils.isNumber(authorQ) ? this.authorService.find(MonocleNumberUtils.safeParseLong(authorQ)) : this.authorService.findAuthorByName(authorQ);
        if (author.isEmpty()) {
            LOGGER.error("No author found for given query: {}", authorQ);
            return Collections.emptyList();
        }

        return Lists.newArrayList(this.articleConverter.convertAll(this.articleService.findArticlesByAuthor(author.get(), createPageAndSort(pageAndSortData))));
    }

    /**
     * Finds a {@link List} of {@link Article}s by their {@link Source}
     *
     * @param sourceQ         a string that may contain an source's code or name
     * @param pageAndSortData pagination and sort data
     * @return a {@link List} of {@link Article}s with the given {@link Source}
     */
    public List<ArticleResource> findArticlesBySource(String sourceQ, MonoclePageAndSortData pageAndSortData) {
        if (StringUtils.isEmpty(sourceQ)) {
            LOGGER.error("source was null or empty");
            return Collections.emptyList();
        }

        Optional<Source> source = MonocleNumberUtils.isNumber(sourceQ) ? this.sourceService.find(MonocleNumberUtils.safeParseLong(sourceQ)) : this.sourceService.findSourceByName(sourceQ);
        if (source.isEmpty()) {
            LOGGER.error("No source found for given query: {}", sourceQ);
            return Collections.emptyList();
        }

        return Lists.newArrayList(this.articleConverter.convertAll(this.articleService.findArticlesBySource(source.get(), createPageAndSort(pageAndSortData))));
    }

    /**
     * Finds an {@link Article} for the given url
     *
     * @param url article's url
     * @return an {@link ArticleResource} with the matching url
     */
    public ArticleResource findArticleByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("url was null or empty");
            return new ArticleResource();
        }

        Optional<Article> article = this.articleService.findArticleByUrl(url);
        if (article.isPresent()) {
            return this.articleConverter.convert(article.get());
        }

        LOGGER.error("No article found for url {}", url);
        return new ArticleResource();
    }

    /**
     * Finds an {@link Article} for the given title
     *
     * @param title           article's title
     * @param pageAndSortData pagination and sort data
     * @return a {@link List} of {@link ArticleResource}s that best match the given title
     */
    public List<ArticleResource> findArticleByTitle(String title, MonoclePageAndSortData pageAndSortData) {
        if (StringUtils.isEmpty(title)) {
            LOGGER.error("title was null or empty");
            return Collections.emptyList();
        }

        return Lists.newArrayList(this.articleConverter.convertAll(this.articleService.findArticleByTitle(title, createPageAndSort(pageAndSortData))));
    }

    /**
     * Finds an {@link Article} for the given query. Supports url and title search
     *
     * @param articleQ        article query
     * @param language        {@link MonocleLanguage} as a string code
     * @param pageAndSortData pagination and sort data
     * @return a {@link List} of {@link Article}s matching the query
     */
    public List<ArticleResource> findArticle(String articleQ, String language, MonoclePageAndSortData pageAndSortData) {

        if (StringUtils.isEmpty(articleQ)) {
            LOGGER.error("article was null or empty");
            return Collections.emptyList();
        }

        if (MonocleUtils.isValidUrl(articleQ)) {
            Optional<Article> article = this.articleService.findArticleByUrl(articleQ);
            if (article.isPresent()) {
                return Lists.newArrayList(this.articleConverter.convert(article.get()));
            } else {
                return urlSearchAndResolve(
                        articleQ,
                        new String[]{
                                MonocleCoreConstants.Client.Q,
                                MonocleCoreConstants.Client.LANGUAGE,
                                MonocleCoreConstants.Client.DOMAINS,
                                MonocleCoreConstants.Client.PAGE_SIZE
                        }, new String[]{
                                computeUrlSearchQuery(articleQ),
                                (StringUtils.isEmpty(language) || !EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, language)) ? "en" : language,
                                computeDomain(articleQ),
                                String.valueOf(100)
                        }
                );
            }
        }

        return searchAndResolve(
                new String[]{
                        MonocleCoreConstants.Client.Q_IN_TITLE,
                        MonocleCoreConstants.Client.LANGUAGE,
                        MonocleCoreConstants.Client.PAGE,
                        MonocleCoreConstants.Client.PAGE_SIZE,
                        MonocleCoreConstants.Client.SORT_BY
                }, new String[]{
                        articleQ,
                        (StringUtils.isEmpty(language) || !EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, language)) ? "en" : language,
                        String.valueOf(pageAndSortData.getPageNumber()),
                        String.valueOf(pageAndSortData.getPageSize()),
                        MonocleClientSort.PUBLISHED_AT.getCode()
                }
        );
    }


    //  HELPERS

    /**
     * Attempts to compute the {@link Source} of the url
     *
     * @param url article url
     * @return source's identifier
     */
    private String computeDomain(String url) {
        if (StringUtils.isNotEmpty(url) && MonocleUtils.isValidUrl(url)) {
            String domain;
            try {
                domain = new URI(url).getHost();
                return domain.replace("www.", StringUtils.EMPTY);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return StringUtils.EMPTY;
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * Computes a search query from an article's url
     *
     * @param url article url
     * @return a string search query
     */
    private String computeUrlSearchQuery(String url) {
        if (StringUtils.isEmpty(url) || !MonocleUtils.isValidUrl(url)) {
            return StringUtils.EMPTY;
        }

        String path;
        try {
            path = new URI(url).getPath();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return StringUtils.EMPTY;
        }

        String[] array = path.split("/");
        List<String> strings =
                Arrays.stream(array)
                        .filter(s -> !NumberUtils.isCreatable(s))
                        .filter(StringUtils::isNotEmpty)
                        .sorted(Comparator.comparingInt(String::length).thenComparingInt(s -> calculateOccurrences(s, '-')).reversed())
                        .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(strings)) {
            return StringUtils.EMPTY;
        }

        strings =
                Arrays.stream(strings.get(0).split("-"))
                        .filter(s -> !MonocleCoreConstants.Lexicon.THIRTY_STOP_WORDS.contains(s.toLowerCase()))
                        .map(s -> "+" + s)
                        .limit(5)
                        .collect(Collectors.toList());

        String result = String.join(" ", strings);
        if (result.indexOf('.') != -1) {
            return result.substring(0, result.indexOf('.'));
        }

        return result;
    }

    /**
     * Calculates the number of occurrences of a character in the given string
     *
     * @param string given string
     * @param c character to search for
     * @return integer count of chars appearing in the string
     */
    private Integer calculateOccurrences(String string, char c) {
        if (StringUtils.isEmpty(string)) {
            return 0;
        }

        char[] chars = string.toCharArray();
        int count = 0;
        for (char aChar : chars) {
            if (aChar == c) {
                count += 1;
            }
        }

        return count;
    }

    /**
     * Performs the search using {@link MonocleNewsAPIClient}
     *
     * @param params params to search with
     * @param values param values
     * @return {@link GenericSearchResponse}
     */
    private GenericSearchResponse search(String[] params, String[] values) {
        if (values.length > 0 && params.length == values.length) {
            Map<String, Object> titleSearchParams = new HashMap<>();

            for (int i = 0; i < params.length; i++) {
                titleSearchParams.put(params[i], values[i]);
            }

            return this.monocleNewsAPIClient.fetchEverything(this.monocleClientRequestBuilder.buildEverythingRequest(titleSearchParams));
        }

        return new GenericSearchResponse();
    }

    /**
     * Uses {@link MonocleNewsAPIClient} to search for and resolve {@link ArticleResponseData} into {@link ArticleResource}s
     *
     * @param url    article url used for search
     * @param params request parameters
     * @param values request values
     * @return a {@link List} of {@link ArticleResource}s
     */
    private List<ArticleResource> urlSearchAndResolve(String url, String[] params, String[] values) {

        GenericSearchResponse response = search(params, values);
        if (response != null && !response.isEmpty() && response.isValid()) {
            List<ArticleResponseData> data = response.getArticles();
            if (CollectionUtils.isNotEmpty(data)) {
                Optional<ArticleResponseData> article = data.stream().filter(a -> a.getUrl().contains(url)).findFirst();
                if (article.isPresent()) {
                    return Lists.newArrayList(this.articleResolver.resolveResource(article.get(), true));
                } else {
                    return data.stream().map(res -> this.articleResolver.resolveResource(res, false)).collect(Collectors.toList());
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Uses {@link MonocleNewsAPIClient} to search for and resolve {@link ArticleResponseData} into {@link ArticleResource}s
     *
     * @param params request parameters
     * @param values request values
     * @return a {@link List} of {@link ArticleResource}s
     */
    private List<ArticleResource> searchAndResolve(String[] params, String[] values) {
        GenericSearchResponse response = search(params, values);
        if (response != null && !response.isEmpty() && response.isValid()) {
            return response.getArticles().stream().filter(res -> res.getUrl().contains("https")).map(res -> this.articleResolver.resolveResource(res, false)).collect(Collectors.toList());
        }

        LOGGER.error("No results found for search with params {} and values {}", Arrays.toString(params), Arrays.toString(values));//NOSONAR
        return Collections.emptyList();
    }
}
