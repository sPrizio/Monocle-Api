package com.priago.monocleapi.api.controllers.media;

import com.priago.monocleapi.api.configuration.Swagger2Config;
import com.priago.monocleapi.api.controllers.AbstractMonocleController;
import com.priago.monocleapi.api.facades.media.impl.AuthorFacade;
import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.models.data.search.MonoclePageAndSortData;
import com.priago.monocleapi.api.resources.entities.media.impl.AuthorResource;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller that exposes endpoints for handling {@link Author} queries
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Api(tags = {Swagger2Config.AUTHOR_CONTROLLER})
@RestController
@RequestMapping("/api/authors")
public class AuthorController extends AbstractMonocleController<AuthorResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    @Resource(name = "authorFacade")
    private AuthorFacade authorFacade;


    //  METHODS

    //  *************** GET ***************

    /**
     * Returns an {@link AuthorResource} for the given code
     *
     * @param code author's code
     * @return {@link StandardJsonResponse} containing an {@link AuthorResource}
     */
    @GetMapping("/{code}")
    @ApiOperation("Fetch an author by their code/id")
    public StandardJsonResponse getAuthorByCode(final @PathVariable("code") @ApiParam("The author's code/id") String code) {

        if (StringUtils.isNotEmpty(code)) {
            return findEntity(code, this.authorFacade.find(code));
        }

        LOGGER.error("Invalid author code given {}", code);
        return new StandardJsonResponse(false, null, "Invalid author code given: " + code);
    }

    /**
     * Returns an {@link AuthorResource} for the given name
     *
     * @param name author's name
     * @return {@link StandardJsonResponse} containing an {@link AuthorResource}
     */
    @GetMapping("/get-author")
    @ApiOperation("Fetch an author by their name")
    public StandardJsonResponse getAuthorByName(final @RequestParam("name") @ApiParam("The author's name") String name) {

        if (StringUtils.isEmpty(name)) {
            LOGGER.error("name was null or empty");
            return new StandardJsonResponse(false, null, "name was null or empty");
        }

        AuthorResource resource = this.authorFacade.findAuthorByName(name);
        if (resource.isPresent()) {
            return new StandardJsonResponse(true, resource, StringUtils.EMPTY);
        }

        return new StandardJsonResponse(false, null, "No author found for name " + name);
    }

    /**
     * Returns all {@link AuthorResource}s
     *
     * @param currentPage the current page of results to return
     * @param pageSize the number of results to return for the page
     * @param attribute  the attribute to sort by
     * @param order the sort direction, ascending or descending
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link AuthorResource}
     */
    @GetMapping("/all")
    @ApiOperation("Fetch all Authors in Monocle")
    public StandardJsonResponse getAllAuthors(
            final @RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_PAGE) @ApiParam("The current result page requested") int currentPage,
            final @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) @ApiParam("The number of results returned per page") int pageSize,
            final @RequestParam(value = "attribute", required = false, defaultValue = DEFAULT_SORT_ATTRIBUTE) @ApiParam("The attribute to sort by") String attribute,
            final @RequestParam(value = "order", required = false, defaultValue = DEFAULT_SORT_ORDER) @ApiParam("The sort order, asc or desc") String order) {

        MonoclePageAndSortData pageAndSortData = new MonoclePageAndSortData(currentPage, pageSize, attribute, order);
        return new StandardJsonResponse(true, this.authorFacade.findAll(pageAndSortData), StringUtils.EMPTY);
    }
}
