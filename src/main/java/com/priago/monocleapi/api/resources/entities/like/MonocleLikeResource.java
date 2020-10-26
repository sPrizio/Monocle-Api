package com.priago.monocleapi.api.resources.entities.like;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;

/**
 * Class representation of a 'like'/'dislike' which can be loosely defined as a {@link UserResource}'s interaction with a piece of media
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleLikeResource extends MonocleResource {

    /**
     * Returns the {@link MonocleMediaResource} that was interacted with
     *
     * @return {@link MonocleMediaResource}
     */
    MonocleMediaResource getResource();

    /**
     * Returns the {@link UserResource} that interacted with the given entity
     *
     * @return {@link UserResource}
     */
    UserResource getUser();

    /**
     * Returns true if the like was a positive reaction
     *
     * @return false if not a like
     */
    boolean isLike();

    /**
     * Returns true if the like was a negative reaction
     *
     * @return false if a like
     */
    boolean isDislike();
}
