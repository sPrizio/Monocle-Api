package com.priago.monocleapi.client.builder.request.impl;

import com.priago.monocleapi.client.builder.request.MonocleClientRequestBuilder;
import com.priago.monocleapi.client.models.request.impl.EverythingRequest;
import com.priago.monocleapi.client.models.request.impl.SourcesRequest;
import com.priago.monocleapi.client.models.request.impl.TopHeadlinesRequest;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleClientSort;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.services.nonentities.DateTimeService;
import com.priago.monocleapi.core.util.MonocleNumberUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Client.*;
import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Core.ISO_DATE_TIME_FORMAT;

/**
 * Default implementation of {@link MonocleClientRequestBuilder}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("monocleClientRequestBuilder")
public class DefaultMonocleClientRequestBuilder implements MonocleClientRequestBuilder {

    @Resource(name = "dateTimeService")
    private DateTimeService dateTimeService;


    //  METHODS

    @Override
    public TopHeadlinesRequest buildTopHeadlinesRequest(Map<String, Object> params) {

        if (MapUtils.isEmpty(params)) {
            return new TopHeadlinesRequest();
        }

        Map<String, String> validParams = new HashMap<>();

        //  country
        if (params.containsKey(COUNTRY) && EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, params.get(COUNTRY).toString())) {
            validParams.put(COUNTRY, params.get(COUNTRY).toString());
        }

        //  category
        if (params.containsKey(CATEGORY) && EnumUtils.isValidEnumIgnoreCase(MonocleCategory.class, params.get(CATEGORY).toString())) {
            validParams.put(CATEGORY, params.get(CATEGORY).toString());
        }

        //  sources
        if (params.containsKey(SOURCES)) {
            //  ensure comma separated
            validParams.put(SOURCES, String.join(",", params.get(SOURCES).toString().split(",")));
        }

        //  q
        if (params.containsKey(Q)) {
            validParams.put(Q, URLEncoder.encode(params.get(Q).toString(), StandardCharsets.UTF_8));
        }

        handlePagination(params, validParams);
        return new TopHeadlinesRequest(validParams);
    }

    @Override
    public EverythingRequest buildEverythingRequest(Map<String, Object> params) {

        if (MapUtils.isEmpty(params)) {
            return new EverythingRequest();
        }

        Map<String, String> validParams = new HashMap<>();

        //  q
        if (params.containsKey(Q)) {
            validParams.put(Q, URLEncoder.encode(params.get(Q).toString(), StandardCharsets.UTF_8));
        }

        //  q in title
        if (params.containsKey(Q_IN_TITLE)) {
            validParams.put(Q_IN_TITLE, URLEncoder.encode(params.get(Q_IN_TITLE).toString(), StandardCharsets.UTF_8));
        }

        //  sources
        if (params.containsKey(SOURCES)) {
            //  ensure comma separated
            validParams.put(SOURCES, String.join(",", params.get(SOURCES).toString().split(",")));
        }

        //  domains
        if (params.containsKey(DOMAINS)) {
            //  ensure comma separated
            validParams.put(DOMAINS, String.join(",", params.get(DOMAINS).toString().split(",")));
        }

        //  exclude domains
        if (params.containsKey(EXCLUDE_DOMAINS)) {
            //  ensure comma separated
            validParams.put(EXCLUDE_DOMAINS, String.join(",", params.get(EXCLUDE_DOMAINS).toString().split(",")));
        }

        //  from
        if (params.containsKey(FROM) && this.dateTimeService.parseDateTimeStringForFormat(params.get(FROM).toString(), DateTimeFormatter.ofPattern(ISO_DATE_TIME_FORMAT)) != null) {
            validParams.put(FROM, params.get(FROM).toString());
        }

        //  to
        if (params.containsKey(TO)  && this.dateTimeService.parseDateTimeStringForFormat(params.get(TO).toString(), DateTimeFormatter.ofPattern(ISO_DATE_TIME_FORMAT)) != null) {
            validParams.put(TO, params.get(TO).toString());
        }

        //  language
        if (params.containsKey(LANGUAGE) && EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, params.get(LANGUAGE).toString())) {
            validParams.put(LANGUAGE, params.get(LANGUAGE).toString());
        }

        //  sort by
        if (params.containsKey(SORT_BY) && EnumUtils.isValidEnumIgnoreCase(MonocleClientSort.class, params.get(SORT_BY).toString())) {
            validParams.put(SORT_BY, params.get(SORT_BY).toString());
        }

        handlePagination(params, validParams);
        return new EverythingRequest(validParams);
    }

    @Override
    public SourcesRequest buildSourcesRequest(Map<String, Object> params) {

        if (MapUtils.isEmpty(params)) {
            return new SourcesRequest();
        }

        Map<String, String> validParams = new HashMap<>();

        //  category
        if (params.containsKey(CATEGORY) && EnumUtils.isValidEnumIgnoreCase(MonocleCategory.class, params.get(CATEGORY).toString())) {
            validParams.put(CATEGORY, params.get(CATEGORY).toString());
        }

        //  language
        if (params.containsKey(LANGUAGE) && EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, params.get(LANGUAGE).toString())) {
            validParams.put(LANGUAGE, params.get(LANGUAGE).toString());
        }

        //  country
        if (params.containsKey(COUNTRY) && EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, params.get(COUNTRY).toString())) {
            validParams.put(COUNTRY, params.get(COUNTRY).toString());
        }

        return new SourcesRequest(validParams);
    }


    //  HELPERS

    /**
     * Handles the pagination info that is repeated across requests
     *
     * @param params incoming request parameters that need to validated
     * @param validParams validated and sanitized request parameters ready to be used to build a valid NewsAPI request
     */
    private void handlePagination(final Map<String, Object> params, final Map<String, String> validParams) {
        //  pageSize
        if (params.containsKey(PAGE_SIZE) && (MonocleNumberUtils.safeParseInteger(params.get(PAGE_SIZE).toString()) != -1)) {
            validParams.put(PAGE_SIZE, params.get(PAGE_SIZE).toString());
        }

        //  page
        if (params.containsKey(PAGE) && (MonocleNumberUtils.safeParseInteger(params.get(PAGE).toString()) != -1)) {
            validParams.put(PAGE, String.valueOf(Integer.parseInt(params.get(PAGE).toString()) + 1));
        }
    }
}
