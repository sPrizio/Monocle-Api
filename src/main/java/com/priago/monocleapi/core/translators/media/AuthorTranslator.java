package com.priago.monocleapi.core.translators.media;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.translators.AbstractMonocleTranslator;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Translates a map into an {@link Author} entity
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("authorTranslator")
public class AuthorTranslator extends AbstractMonocleTranslator implements MonocleTranslator<Author> {

    @Resource(name = "sourceTranslator")
    private SourceTranslator sourceTranslator;


    //  METHODS

    @Override
    public Author translate(Map<String, Object> values) {
        if (MapUtils.isEmpty(values)) {
            return null;
        }

        Author author = new Author();

        author.setSource(this.sourceTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.Client.SOURCE)));
        author.setCode(getMapString(values, MonocleCoreConstants.Client.CODE));
        author.setName(getMapString(values, MonocleCoreConstants.Client.NAME));

        return author;
    }
}
