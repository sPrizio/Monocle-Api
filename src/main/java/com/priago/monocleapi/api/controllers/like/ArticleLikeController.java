package com.priago.monocleapi.api.controllers.like;

import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.resources.entities.like.impl.ArticleLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that exposes endpoints for liking {@link Article}s
 * NOTE: no swagger api documentation will be included since these will not be public endpoints
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleLikeController extends AbstractMonocleLikeController<ArticleLikeResource> {

    //  METHODS

    //  *************** POST ***************

    /**
     * Likes an article
     *
     * @param articleUid article's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/like")
    public StandardJsonResponse like(final @RequestParam("article") String articleUid) {
        return add("", articleUid, MonocleLikeCardinality.LIKE);
    }

    /**
     * Dislikes an article
     *
     * @param articleUid article's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/dislike")
    public StandardJsonResponse dislike(final @RequestParam("article") String articleUid) {
        return add("", articleUid, MonocleLikeCardinality.DISLIKE);
    }

    /**
     * Removes an article like
     *
     * @param articleUid article's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/remove-like")
    public StandardJsonResponse removeLike(final @RequestParam("article") String articleUid) {
        return remove("", articleUid, MonocleLikeCardinality.LIKE);
    }

    /**
     * Removes an article dislike
     *
     * @param articleUid article's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/remove-dislike")
    public StandardJsonResponse removeDislike(final @RequestParam("article") String articleUid) {
        return remove("", articleUid, MonocleLikeCardinality.DISLIKE);
    }
}
