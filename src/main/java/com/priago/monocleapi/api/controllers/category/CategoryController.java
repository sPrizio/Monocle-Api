package com.priago.monocleapi.api.controllers.category;

import com.priago.monocleapi.api.configuration.Swagger2Config;
import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.models.data.category.CategoryData;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.client.builder.request.MonocleClientRequestBuilder;
import com.priago.monocleapi.client.models.response.impl.GenericSearchResponse;
import com.priago.monocleapi.client.newsapi.MonocleNewsAPIClient;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller that exposes endpoints for handling category queries
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Api(tags = {Swagger2Config.CATEGORY_CONTROLLER})
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Resource(name = "monocleClientRequestBuilder")
    private MonocleClientRequestBuilder monocleClientRequestBuilder;

    @Resource(name = "monocleNewsAPIClient")
    private MonocleNewsAPIClient monocleNewsAPIClient;


    //  METHODS

    //  *************** GET ***************

    /**
     * Returns all categories that Monocle supports
     *
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link CategoryData}
     */
    @GetMapping
    @ApiOperation("Fetch all categories that Monocle actively supports")
    public StandardJsonResponse getCategories() {
        return new StandardJsonResponse(true, Arrays.stream(MonocleCategory.values()).map(CategoryData::new).collect(Collectors.toList()), StringUtils.EMPTY);
    }

    /**
     * Returns the top headlines for a country
     *
     * @param country {@link MonocleCountry} as a string
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link ArticleResource}
     */
    @GetMapping("/top-headlines")
    @ApiOperation("Fetch top headlines for all active headlines")
    public StandardJsonResponse getCategoriesTopHeadlines(final @RequestParam("country") @ApiParam("The desired country's 2-letter ISO 3166-1 code") String country) {
        Map<String, Object> params = new HashMap<>();
        if (EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, country)) {
            params.put(MonocleCoreConstants.Client.COUNTRY, country);
        }

        GenericSearchResponse response = this.monocleNewsAPIClient.fetchTopHeadlines(this.monocleClientRequestBuilder.buildTopHeadlinesRequest(params));
        if (!response.isEmpty() && response.isValid()) {
            return new StandardJsonResponse(true, response.getArticles(), StringUtils.EMPTY);
        } else {
            return new StandardJsonResponse(false, null, response.getMessage());
        }
    }

    /**
     * Returns the top headlines for a country and category
     *
     * @param categoryCode {@link MonocleCategory}'s code
     * @param country {@link MonocleCountry} as a string
     * @return {@link StandardJsonResponse} containing a {@link List} of {@link ArticleResource}
     */
    @GetMapping("/{categoryCode}/top-headlines")
    @ApiOperation("Fetch a category's top headlines")
    public StandardJsonResponse getCategoryTopHeadlines(
            final @PathVariable("categoryCode") String categoryCode,
            final @RequestParam("country") @ApiParam("The desired country's 2-letter ISO 3166-1 code") String country
    ) {
        if (!EnumUtils.isValidEnumIgnoreCase(MonocleCategory.class, categoryCode)) {
            return new StandardJsonResponse(false, null, "The given category code was invalid");
        }

        Map<String, Object> params = new HashMap<>();
        if (EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, country)) {
            params.put(MonocleCoreConstants.Client.CATEGORY, categoryCode);
            params.put(MonocleCoreConstants.Client.COUNTRY, country);
        }

        GenericSearchResponse response = this.monocleNewsAPIClient.fetchTopHeadlines(this.monocleClientRequestBuilder.buildTopHeadlinesRequest(params));
        if (!response.isEmpty() && response.isValid()) {
            return new StandardJsonResponse(true, response.getArticles(), StringUtils.EMPTY);
        } else {
            return new StandardJsonResponse(false, null, response.getMessage());
        }
    }
}
