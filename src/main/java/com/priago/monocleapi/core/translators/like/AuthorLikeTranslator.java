package com.priago.monocleapi.core.translators.like;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.models.entities.like.impl.AuthorLike;
import com.priago.monocleapi.core.translators.AbstractMonocleTranslator;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import com.priago.monocleapi.core.translators.media.AuthorTranslator;
import com.priago.monocleapi.core.translators.user.UserTranslator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Translates a map into an {@link AuthorLike} entity
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("authorLikeTranslator")
public class AuthorLikeTranslator extends AbstractMonocleTranslator implements MonocleTranslator<AuthorLike> {

    @Resource(name = "authorTranslator")
    private AuthorTranslator authorTranslator;

    @Resource(name = "userTranslator")
    private UserTranslator userTranslator;


    //  METHODS

    @Override
    public AuthorLike translate(Map<String, Object> values) {
        if (MapUtils.isEmpty(values)) {
            return null;
        }

        AuthorLike like = new AuthorLike();

        like.setCardinality(getMapString(values, MonocleCoreConstants.UI.CARDINALITY));
        like.setEntity(this.authorTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.UI.ENTITY)));
        like.setUser(this.userTranslator.translate((Map<String, Object>) getMapObject(values, MonocleCoreConstants.UI.USER)));

        return like;
    }
}
