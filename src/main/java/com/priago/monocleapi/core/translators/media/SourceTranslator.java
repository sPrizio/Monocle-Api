package com.priago.monocleapi.core.translators.media;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.translators.AbstractMonocleTranslator;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Translates a map into a {@link Source} entity
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceTranslator")
public class SourceTranslator extends AbstractMonocleTranslator implements MonocleTranslator<Source> {


    //  METHODS

    @Override
    public Source translate(Map<String, Object> values) {
        if (MapUtils.isEmpty(values)) {
            return null;
        }

        Source source = new Source();

        source.setId(getMapString(values, MonocleCoreConstants.Client.ID));
        source.setName(getMapString(values, MonocleCoreConstants.Client.NAME));
        source.setDescription(getMapString(values, MonocleCoreConstants.Client.DESCRIPTION));
        source.setUrl(getMapString(values, MonocleCoreConstants.Client.URL));
        source.setCategory(getMapString(values, MonocleCoreConstants.Client.CATEGORY));
        source.setLanguage(getMapString(values, MonocleCoreConstants.Client.LANGUAGE));
        source.setCountry(getMapString(values, MonocleCoreConstants.Client.COUNTRY));

        return source;
    }
}
