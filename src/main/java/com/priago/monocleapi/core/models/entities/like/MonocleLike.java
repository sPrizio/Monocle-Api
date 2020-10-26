package com.priago.monocleapi.core.models.entities.like;

import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import com.priago.monocleapi.core.models.entities.user.User;

/**
 * Class representation of a 'like'/'dislike' which can be loosely defined as a {@link User}'s interaction with a piece of media
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleLike extends MonocleEntity {

    /**
     * Returns the {@link MonocleMedia} that was interacted with
     *
     * @return {@link MonocleMedia}
     */
    MonocleMedia getEntity();

    /**
     * Returns the {@link User} that interacted with the given entity
     *
     * @return {@link User}
     */
    User getUser();

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
