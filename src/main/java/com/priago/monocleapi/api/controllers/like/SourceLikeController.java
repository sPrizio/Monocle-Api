package com.priago.monocleapi.api.controllers.like;

import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.resources.entities.like.impl.SourceLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that exposes various endpoints for liking {@link Source}s
 * NOTE: no swagger api documentation will be included since these will not be public endpoints
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/api/sources")
public class SourceLikeController extends AbstractMonocleLikeController<SourceLikeResource> {


    //  METHODS

    //  *************** POST ***************

    /**
     * Likes a source
     *
     * @param sourceUid source's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/like")
    public StandardJsonResponse like(final @RequestParam("source") String sourceUid) {
        return add("", sourceUid, MonocleLikeCardinality.LIKE);
    }

    /**
     * Dislikes a source
     *
     * @param sourceUid source's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/dislike")
    public StandardJsonResponse dislike(final @RequestParam("source") String sourceUid) {
        return add("", sourceUid, MonocleLikeCardinality.DISLIKE);
    }

    /**
     * Removes a source like
     *
     * @param sourceUid source's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/remove-like")
    public StandardJsonResponse removeLike(final @RequestParam("source") String sourceUid) {
        return remove("", sourceUid, MonocleLikeCardinality.LIKE);
    }

    /**
     * Removes a source dislike
     *
     * @param sourceUid source's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/remove-dislike")
    public StandardJsonResponse removeDislike(final @RequestParam("source") String sourceUid) {
        return remove("", sourceUid, MonocleLikeCardinality.DISLIKE);
    }
}
