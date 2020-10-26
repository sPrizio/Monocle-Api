package com.priago.monocleapi.client.builder.response.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priago.monocleapi.client.builder.data.impl.ArticleResponseDataBuilder;
import com.priago.monocleapi.client.builder.data.impl.SourceResponseDataBuilder;
import com.priago.monocleapi.client.builder.response.MonocleClientResponseBuilder;
import com.priago.monocleapi.client.models.data.impl.ArticleResponseData;
import com.priago.monocleapi.client.models.data.impl.SourceResponseData;
import com.priago.monocleapi.client.models.response.impl.GenericSearchResponse;
import com.priago.monocleapi.client.models.response.impl.SourcesResponse;
import com.priago.monocleapi.core.enums.MonocleStatus;
import com.priago.monocleapi.core.util.MonocleNumberUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Client.*;

/**
 * Default implementation of {@link MonocleClientResponseBuilder}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("monocleClientResponseBuilder")
public class DefaultMonocleClientResponseBuilder implements MonocleClientResponseBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMonocleClientResponseBuilder.class);

    @Resource(name = "articleResponseDataBuilder")
    private ArticleResponseDataBuilder articleResponseDataBuilder;

    @Resource(name = "sourceResponseDataBuilder")
    private SourceResponseDataBuilder sourceResponseDataBuilder;


    //  METHODS

    @Override
    public GenericSearchResponse handleGenericSearchResponse(String response) {

        if (StringUtils.isEmpty(response)) {
            LOGGER.error("Response from NewsAPI failed {}", response);
            return new GenericSearchResponse();
        }

        try {
            Map<String, Object> values = new ObjectMapper().readValue(response, new TypeReference<>() {});

            MonocleStatus status = null;
            int totalResults = -1;
            List<ArticleResponseData> articles = new ArrayList<>();
            String code = StringUtils.EMPTY;
            String message = StringUtils.EMPTY;

            if (values.containsKey(STATUS) && EnumUtils.isValidEnumIgnoreCase(MonocleStatus.class, values.get(STATUS).toString())) {
                status = MonocleStatus.valueOf(values.get(STATUS).toString().toUpperCase());
            }

            if (values.containsKey(TOTAL_RESULTS)) {
                totalResults = MonocleNumberUtils.safeParseInteger(values.get(TOTAL_RESULTS).toString());
            }

            if (values.containsKey(ARTICLES)) {
                articles = ((List<Map<String, Object>>) values.get(ARTICLES)).stream().map(data -> this.articleResponseDataBuilder.buildResponseData(data)).collect(Collectors.toList());
            }

            if (values.containsKey(CODE)) {
                code = values.get(CODE).toString();
            }

            if (values.containsKey(MESSAGE)) {
                message = values.get(MESSAGE).toString();
            }


            if (status != null && status.equals(MonocleStatus.ERROR)) {
                return new GenericSearchResponse(status, totalResults, articles, code, message);
            } else if (status != null && status.equals(MonocleStatus.OK)) {
                return new GenericSearchResponse(status, totalResults, articles);
            } else {
                return new GenericSearchResponse();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new GenericSearchResponse();
        }
    }

    @Override
    public SourcesResponse handleSourcesResponse(String response) {

        if (StringUtils.isEmpty(response)) {
            LOGGER.error("Response from NewsAPI failed {}", response);
            return new SourcesResponse();
        }

        try {
            Map<String, Object> values = new ObjectMapper().readValue(response, new TypeReference<>() {});

            MonocleStatus status = null;
            List<SourceResponseData> sources = new ArrayList<>();
            String code = StringUtils.EMPTY;
            String message = StringUtils.EMPTY;

            if (values.containsKey(STATUS) && EnumUtils.isValidEnumIgnoreCase(MonocleStatus.class, values.get(STATUS).toString())) {
                status = MonocleStatus.valueOf(values.get(STATUS).toString().toUpperCase());
            }

            if (values.containsKey(SOURCES)) {
                sources = ((List<Map<String, Object>>) values.get(SOURCES)).stream().map(data -> this.sourceResponseDataBuilder.buildResponseData(data)).collect(Collectors.toList());
            }

            if (values.containsKey(CODE)) {
                code = values.get(CODE).toString();
            }

            if (values.containsKey(MESSAGE)) {
                message = values.get(MESSAGE).toString();
            }


            if (status != null && status.equals(MonocleStatus.ERROR)) {
                return new SourcesResponse(status, sources, code, message);
            } else if (status != null && status.equals(MonocleStatus.OK)) {
                return new SourcesResponse(status, sources);
            } else {
                return new SourcesResponse();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new SourcesResponse();
        }
    }
}
