package com.priago.monocleapi.api.controllers.media;

import com.priago.monocleapi.api.configuration.Swagger2Config;
import com.priago.monocleapi.api.controllers.AbstractMonocleController;
import com.priago.monocleapi.api.facades.media.impl.SourceFacade;
import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.models.data.search.MonoclePageAndSortData;
import com.priago.monocleapi.api.resources.entities.media.impl.SourceResource;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
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
 * Controller that exposes endpoints for handling {@link Source} queries
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Api(tags = {Swagger2Config.SOURCE_CONTROLLER})
@RestController
@RequestMapping("/api/sources")
public class SourceController extends AbstractMonocleController<SourceResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceController.class);

    @Resource(name = "sourceFacade")
    private SourceFacade sourceFacade;


    //  METHODS

    //  *************** GET ***************

    /**
     * Returns a {@link SourceResource} for the given code
     *
     * @param code source's code
     * @return {@link StandardJsonResponse} containing a {@link SourceResource}
     */
    @GetMapping("/{code}")
    @ApiOperation("Fetch a source by its code/id")
    public StandardJsonResponse getSourceByCode(final @PathVariable("code") @ApiParam("The source's code/id") String code) {

        if (StringUtils.isNotEmpty(code)) {
            return findEntity(code, this.sourceFacade.find(code));
        }

        LOGGER.error("Invalid source code given {}", code);
        return new StandardJsonResponse(false, null, "Invalid source code given: " + code);
    }

    /**
     * Returns a {@link SourceResource} for the given name
     *
     * @param name source's name
     * @return {@link StandardJsonResponse} containing a {@link SourceResource}
     */
    @GetMapping("/get-source")
    @ApiOperation("Fetch a source by its name")
    public StandardJsonResponse getSourceByName(final @RequestParam("name") @ApiParam("The source's name") String name) {

        if (StringUtils.isEmpty(name)) {
            LOGGER.error("name was null or empty");
            return new StandardJsonResponse(false, null, "name was null or empty");
        }

        SourceResource resource = this.sourceFacade.findSourceByName(name);
        if (resource.isPresent()) {
            return new StandardJsonResponse(true, resource, StringUtils.EMPTY);
        }

        return new StandardJsonResponse(false, null, "No source found for name " + name);
    }

    /**
     * Returns all {@link SourceResource}s
     *
     * @param currentPage the current page of results to return
     * @param pageSize the number of results to return for the page
     * @param attribute  the attribute to sort by
     * @param order the sort direction, ascending or descending
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link SourceResource}s
     */
    @GetMapping("/all")
    @ApiOperation("Fetch all sources in Monocle")
    public StandardJsonResponse getAllSources(
            final @RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_PAGE) @ApiParam("The current result page requested") int currentPage,
            final @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) @ApiParam("The number of results returned per page") int pageSize,
            final @RequestParam(value = "attribute", required = false, defaultValue = DEFAULT_SORT_ATTRIBUTE) @ApiParam("The attribute to sort by") String attribute,
            final @RequestParam(value = "order", required = false, defaultValue = DEFAULT_SORT_ORDER) @ApiParam("The sort order, asc or desc") String order) {

        MonoclePageAndSortData pageAndSortData = new MonoclePageAndSortData(currentPage, pageSize, attribute, order);
        return new StandardJsonResponse(true, this.sourceFacade.findAll(pageAndSortData), StringUtils.EMPTY);
    }
}
