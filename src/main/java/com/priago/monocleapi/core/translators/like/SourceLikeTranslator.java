package com.priago.monocleapi.core.translators.like;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.like.impl.SourceLike;
import com.priago.monocleapi.core.translators.AbstractMonocleTranslator;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import com.priago.monocleapi.core.translators.media.SourceTranslator;
import com.priago.monocleapi.core.translators.user.UserTranslator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Translates a map into a {@link SourceLike} entity
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceLikeTranslator")
public class SourceLikeTranslator extends AbstractMonocleTranslator implements MonocleTranslator<SourceLike> {

    @Resource(name = "sourceTranslator")
    private SourceTranslator sourceTranslator;

    @Resource(name = "userTranslator")
    private UserTranslator userTranslator;


    //  METHODS

    @Override
    public SourceLike translate(Map<String, Object> values) {
        if (MapUtils.isEmpty(values)) {
            return null;
        }

        SourceLike like = new SourceLike();

        like.setCardinality(getMapString(values, MonocleCoreConstants.UI.CARDINALITY));
        like.setEntity(this.sourceTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.UI.ENTITY)));
        like.setUser(this.userTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.UI.USER)));

        return like;
    }
}
