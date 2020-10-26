package com.priago.monocleapi.api.controllers.media;

import com.priago.monocleapi.api.configuration.Swagger2Config;
import com.priago.monocleapi.api.controllers.AbstractMonocleController;
import com.priago.monocleapi.api.facades.media.impl.ArticleFacade;
import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.models.data.search.MonoclePageAndSortData;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.util.MonocleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller that exposes endpoints for article queries
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Api(tags = {Swagger2Config.ARTICLE_CONTROLLER})
@RestController
@RequestMapping("/api/articles")
public class ArticleController extends AbstractMonocleController<ArticleResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Resource(name = "articleFacade")
    private ArticleFacade articleFacade;


    //  METHODS

    //  *************** GET ***************

    /**
     * Returns a {@link List} of {@link ArticleResource} for the given author
     *
     * @param author author to search by
     * @param currentPage the current page of results to return
     * @param pageSize the number of results to return for the page
     * @param attribute  the attribute to sort by
     * @param order the sort direction, ascending or descending
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link ArticleResource}s
     */
    @GetMapping("/get-by-author")
    @ApiOperation("Fetch articles by their author")
    public StandardJsonResponse getArticlesByAuthor(
            final @RequestParam(value = "author") @ApiParam("A string query that supports loose searches, i.e. by author id or name") String author,
            final @RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_PAGE) @ApiParam("The current result page requested") int currentPage,
            final @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) @ApiParam("The number of results returned per page") int pageSize,
            final @RequestParam(value = "attribute", required = false, defaultValue = DEFAULT_SORT_ATTRIBUTE) @ApiParam("The attribute to sort by") String attribute,
            final @RequestParam(value = "order", required = false, defaultValue = DEFAULT_SORT_ORDER) @ApiParam("The sort order, asc or desc") String order) {

        if (StringUtils.isEmpty(author)) {
            LOGGER.error("author was null or empty");
            return new StandardJsonResponse(false, null, "author was null or empty");
        }

        MonoclePageAndSortData pageAndSortData = new MonoclePageAndSortData(currentPage, pageSize, attribute, order);
        return new StandardJsonResponse(true, this.articleFacade.findArticlesByAuthor(author, pageAndSortData), StringUtils.EMPTY);
    }

    /**
     * Returns a {@link List} of {@link ArticleResource} for the source
     *
     * @param source source to search by
     * @param currentPage the current page of results to return
     * @param pageSize the number of results to return for the page
     * @param attribute  the attribute to sort by
     * @param order the sort direction, ascending or descending
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link ArticleResource}s
     */
    @GetMapping("/get-by-source")
    @ApiOperation("Fetch articles by their source")
    public StandardJsonResponse getArticlesBySource(
            final @RequestParam("source") @ApiParam("A string query that supports loose searches, i.e. by source id or name") String source,
            final @RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_PAGE) @ApiParam("The current result page requested") int currentPage,
            final @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) @ApiParam("The number of results returned per page") int pageSize,
            final @RequestParam(value = "attribute", required = false, defaultValue = DEFAULT_SORT_ATTRIBUTE) @ApiParam("The attribute to sort by") String attribute,
            final @RequestParam(value = "order", required = false, defaultValue = DEFAULT_SORT_ORDER) @ApiParam("The sort order, asc or desc") String order) {

        if (StringUtils.isEmpty(source)) {
            LOGGER.error("source was null or empty");
            return new StandardJsonResponse(false, null, "source was null or empty");
        }

        MonoclePageAndSortData pageAndSortData = new MonoclePageAndSortData(currentPage, pageSize, attribute, order);
        return new StandardJsonResponse(true, this.articleFacade.findArticlesBySource(source, pageAndSortData), StringUtils.EMPTY);
    }

    /**
     * Returns a {@link List} of {@link ArticleResource} for the given search query
     *
     * @param query article search query
     * @param language language to search in
     * @param currentPage the current page of results to return
     * @param pageSize the number of results to return for the page
     * @param attribute  the attribute to sort by
     * @param order the sort direction, ascending or descending
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link ArticleResource}s
     */
    @GetMapping("/get")
    @ApiOperation("Fetch article by url or best matching title")
    public StandardJsonResponse getArticle(
            final @RequestParam("query") @ApiParam("A query for articles. Can be either direct urls or a string query that will attempt a best match with article titles") String query,
            final @RequestParam("language") @ApiParam("The 2-letter ISO-639-1 code of the language desired") String language,
            final @RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_PAGE) @ApiParam("The current result page requested") int currentPage,
            final @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) @ApiParam("The number of results returned per page") int pageSize,
            final @RequestParam(value = "attribute", required = false, defaultValue = DEFAULT_SORT_ATTRIBUTE) @ApiParam("The attribute to sort by") String attribute,
            final @RequestParam(value = "order", required = false, defaultValue = DEFAULT_SORT_ORDER) @ApiParam("The sort order, asc or desc") String order) {

        if (StringUtils.isEmpty(query) || StringUtils.isEmpty(language)) {
            LOGGER.error("One or more of the required params was null or empty. query: {}, language: {}", query, language);
            return new StandardJsonResponse(false, null, "One or more of the required params was null or empty. query: " + query + ", language: " + language);
        }

        if (EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, language)) {
            MonoclePageAndSortData pageAndSortData = new MonoclePageAndSortData(currentPage, pageSize, attribute, order);
            return new StandardJsonResponse(true, this.articleFacade.findArticle(query, language, pageAndSortData), StringUtils.EMPTY);
        }

        LOGGER.error("{} was not a valid language", language);
        return new StandardJsonResponse(false, null, language + " was not a valid language");
    }

    /**
     * Returns an {@link ArticleResource} for the given url
     *
     * @param url article's url
     * @return {@link StandardJsonResponse} containing an {@link ArticleResource}
     */
    @GetMapping("/get-by-url")
    @ApiOperation("Fetch an article by its url. Note that this method will not to attempt to look up the article online if Monocle does not have it")
    public StandardJsonResponse getArticleByUrl(final @RequestParam("url") @ApiParam("A valid url where this article can be found") String url) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("url was null or empty");
            return new StandardJsonResponse(false, null, "url was null or empty");
        }

        if (MonocleUtils.isValidUrl(url)) {
            return new StandardJsonResponse(true, this.articleFacade.findArticleByUrl(url), StringUtils.EMPTY);
        }

        LOGGER.error("{} was not a valid url", url);
        return new StandardJsonResponse(false, null, url + " was not a valid url");
    }

    /**
     * Returns a {@link List} of {@link ArticleResource} for the given title (best match)
     *
     * @param title article's title
     * @param currentPage the current page of results to return
     * @param pageSize the number of results to return for the page
     * @param attribute  the attribute to sort by
     * @param order the sort direction, ascending or descending
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link ArticleResource}s
     */
    @GetMapping("/get-by-title")
    @ApiOperation("Fetch articles that best match their titles. Note that this method will not to attempt to look up the articles online if Monocle does not have them")
    public StandardJsonResponse getArticleByTitle(
            final @RequestParam("title") @ApiParam("Keywords or phrasing that might appear in the article's title") String title,
            final @RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_PAGE) @ApiParam("The current result page requested") int currentPage,
            final @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) @ApiParam("The number of results returned per page") int pageSize,
            final @RequestParam(value = "attribute", required = false, defaultValue = DEFAULT_SORT_ATTRIBUTE) @ApiParam("The attribute to sort by") String attribute,
            final @RequestParam(value = "order", required = false, defaultValue = DEFAULT_SORT_ORDER) @ApiParam("The sort order, asc or desc") String order) {

        if (StringUtils.isEmpty(title)) {
            LOGGER.error("title was null or empty");
            return new StandardJsonResponse(false, null, "title was null or empty");
        }

        MonoclePageAndSortData pageAndSortData = new MonoclePageAndSortData(currentPage, pageSize, attribute, order);
        return new StandardJsonResponse(true, this.articleFacade.findArticleByTitle(title, pageAndSortData), StringUtils.EMPTY);
    }
}
