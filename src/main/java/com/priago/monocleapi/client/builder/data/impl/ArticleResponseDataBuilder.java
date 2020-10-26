package com.priago.monocleapi.client.builder.data.impl;

import com.priago.monocleapi.client.builder.data.MonocleResponseDataBuilder;
import com.priago.monocleapi.client.models.data.impl.ArticleResponseData;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.http.HttpResponse;
import java.util.Map;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Client.*;

/**
 * Builds an {@link ArticleResponseData} from an {@link HttpResponse}
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("articleResponseDataBuilder")
public class ArticleResponseDataBuilder extends AbstractResponseDataBuilder implements MonocleResponseDataBuilder<ArticleResponseData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleResponseDataBuilder.class);

    @Resource(name = "sourceResponseDataBuilder")
    private SourceResponseDataBuilder sourceResponseDataBuilder;


    //  METHODS

    @Override
    public ArticleResponseData buildResponseData(Map<String, Object> data) {
        if (MapUtils.isEmpty(data)) {
            return new ArticleResponseData();
        }

        try {
            return new ArticleResponseData(
                    this.sourceResponseDataBuilder.buildResponseData((Map<String, Object>) getMapObject(data, SOURCE)),
                    getMapString(data, AUTHOR),
                    getMapString(data, TITLE),
                    getMapString(data, DESCRIPTION),
                    getMapString(data, URL),
                    getMapString(data, URL_TO_IMAGE),
                    getMapString(data, PUBLISHED_AT),
                    getMapString(data, CONTENT)
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ArticleResponseData();
        }
    }
}
