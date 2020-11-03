package com.priago.monocleapi.api.controllers.like;

import com.priago.monocleapi.api.controllers.AbstractMonocleController;
import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.resources.entities.like.MonocleLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.services.nonentities.MonocleLikeCacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Controller
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component
public abstract class AbstractMonocleLikeController<L extends MonocleLikeResource> extends AbstractMonocleController<L> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMonocleLikeController.class);
    private static final String EMPTY_UID = "uid was null or empty";

    @Resource(name = "monocleLikeCacheService")
    private MonocleLikeCacheService monocleLikeCacheService;


    //  METHODS

    /**
     * Adds a media like to the cache
     *
     * @param userUid user's uid
     * @param uid media's uid
     * @param cardinality like/dislike
     * @return {@link StandardJsonResponse}
     */
    StandardJsonResponse add(String userUid, String uid, MonocleLikeCardinality cardinality) {
        if (StringUtils.isEmpty(uid)) {
            LOGGER.error(EMPTY_UID);
            return new StandardJsonResponse(false, null, EMPTY_UID);
        }

        this.monocleLikeCacheService.add(userUid, uid, cardinality);
        return new StandardJsonResponse(true, null, StringUtils.EMPTY);
    }

    /**
     * Removes a media like from the cache
     *
     * @param userUid user's uid
     * @param uid media's uid
     * @param cardinality like/dislike
     * @return {@link StandardJsonResponse}
     */
    StandardJsonResponse remove(String userUid, String uid, MonocleLikeCardinality cardinality) {
        if (StringUtils.isEmpty(uid)) {
            LOGGER.error(EMPTY_UID);
            return new StandardJsonResponse(false, null, EMPTY_UID);
        }

        this.monocleLikeCacheService.remove(userUid, uid, cardinality);
        return new StandardJsonResponse(true, null, StringUtils.EMPTY);
    }
}
