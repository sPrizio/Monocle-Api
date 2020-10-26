package com.priago.monocleapi.client.builder.data.impl;

import com.priago.monocleapi.client.builder.data.MonocleResponseDataBuilder;
import com.priago.monocleapi.client.models.data.impl.SourceResponseData;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.Map;

import static com.priago.monocleapi.core.constants.MonocleCoreConstants.Client.*;


/**
 * Builds a {@link SourceResponseData} from an {@link HttpResponse}
 *
 * <a href="https://newsapi.org/docs">NewsAPI Documentation</a>
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceResponseDataBuilder")
public class SourceResponseDataBuilder extends AbstractResponseDataBuilder implements MonocleResponseDataBuilder<SourceResponseData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceResponseDataBuilder.class);


    //  METHODS

    @Override
    public SourceResponseData buildResponseData(Map<String, Object> data) {
        if (MapUtils.isEmpty(data)) {
            return new SourceResponseData();
        }

        try {
            return new SourceResponseData(
                    getMapString(data, ID),
                    getMapString(data, NAME),
                    getMapString(data, DESCRIPTION),
                    getMapString(data, URL),
                    EnumUtils.isValidEnumIgnoreCase(MonocleCategory.class, getMapString(data, CATEGORY)) ? MonocleCategory.valueOf(getMapString(data, CATEGORY)) : null,
                    EnumUtils.isValidEnumIgnoreCase(MonocleLanguage.class, getMapString(data, LANGUAGE)) ? MonocleLanguage.valueOf(getMapString(data, LANGUAGE)) : null,
                    EnumUtils.isValidEnumIgnoreCase(MonocleCountry.class, getMapString(data, COUNTRY)) ? MonocleCountry.valueOf(getMapString(data, COUNTRY)) : null
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new SourceResponseData();
        }
    }
}
