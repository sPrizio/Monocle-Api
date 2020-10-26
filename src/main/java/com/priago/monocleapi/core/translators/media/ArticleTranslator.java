package com.priago.monocleapi.core.translators.media;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.services.nonentities.DateTimeService;
import com.priago.monocleapi.core.translators.AbstractMonocleTranslator;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Translates a map into an {@link Article} entity
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("articleTranslator")
public class ArticleTranslator extends AbstractMonocleTranslator implements MonocleTranslator<Article> {

    @Resource(name = "authorTranslator")
    private AuthorTranslator authorTranslator;

    @Resource(name = "dateTimeService")
    private DateTimeService dateTimeService;

    @Resource(name = "sourceTranslator")
    private SourceTranslator sourceTranslator;


    //  METHODS

    @Override
    public Article translate(Map<String, Object> values) {
        if (MapUtils.isEmpty(values)) {
            return null;
        }

        Article article = new Article();

        article.setSource(this.sourceTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.Client.SOURCE)));
        article.setAuthor(this.authorTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.Client.AUTHOR)));
        article.setTitle(getMapString(values, MonocleCoreConstants.Client.TITLE));
        article.setDescription(getMapString(values, MonocleCoreConstants.Client.DESCRIPTION));
        article.setUrl(getMapString(values, MonocleCoreConstants.Client.URL));
        article.setUrlToImage(getMapString(values, MonocleCoreConstants.Client.URL_TO_IMAGE));
        article.setPublishedAt(
                this.dateTimeService.parseDateTimeStringForFormat(
                        getMapString(values, MonocleCoreConstants.Client.PUBLISHED_AT), DateTimeFormatter.ofPattern(MonocleCoreConstants.Core.ISO_DATE_TIME_FORMAT)
                )
        );
        article.setContent(getMapString(values, MonocleCoreConstants.Client.CONTENT));

        return article;
    }
}
