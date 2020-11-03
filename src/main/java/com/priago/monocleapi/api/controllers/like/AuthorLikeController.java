package com.priago.monocleapi.api.controllers.like;

import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.resources.entities.like.impl.AuthorLikeResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that exposes endpoints for liking {@link Author}s
 * NOTE: no swagger api documentation will be included since these will not be public endpoints
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorLikeController extends AbstractMonocleLikeController<AuthorLikeResource> {

    //  METHODS

    //  *************** POST ***************

    /**
     * Likes an author
     *
     * @param authorUid author's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/like")
    public StandardJsonResponse like(final @RequestParam("author") String authorUid) {
        return add("", authorUid, MonocleLikeCardinality.LIKE);
    }

    /**
     * Dislikes an author
     *
     * @param authorUid author's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/dislike")
    public StandardJsonResponse dislike(final @RequestParam("author") String authorUid) {
        return add("", authorUid, MonocleLikeCardinality.DISLIKE);
    }

    /**
     * Removes an author like
     *
     * @param authorUid author's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/remove-like")
    public StandardJsonResponse removeLike(final @RequestParam("author") String authorUid) {
        return remove("", authorUid, MonocleLikeCardinality.LIKE);
    }

    /**
     * Removes an author dislike
     *
     * @param authorUid author's uid
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/remove-dislike")
    public StandardJsonResponse removeDislike(final @RequestParam("author") String authorUid) {
        return remove("", authorUid, MonocleLikeCardinality.DISLIKE);
    }
}
