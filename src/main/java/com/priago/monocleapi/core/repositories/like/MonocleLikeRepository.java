package com.priago.monocleapi.core.repositories.like;

import com.priago.monocleapi.core.models.entities.like.MonocleLike;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import com.priago.monocleapi.core.models.entities.user.User;

import java.util.Set;

/**
 * Defines the repository structure for {@link MonocleLike} entities
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleLikeRepository<M extends MonocleMedia> {

    /**
     * Returns the number of positive likes for the given {@link M}
     *
     * @param media {@link M} to consider
     * @return number of positive likes
     */
    Integer getLikes(M media);

    /**
     * Returns the number of negative likes for the given {@link M}
     *
     * @param media {@link M} to consider
     * @return number of negative likes
     */
    Integer getDislikes(M media);

    /**
     * Returns a {@link Set} of {@link User}s that liked the given {@link M}
     *
     * @param media {@link M}
     * @return {@link Set} of {@link User}s
     */
    Set<User> likedBy(M media);

    /**
     * Returns a {@link Set} of {@link User}s that dislike the given {@link M}
     *
     * @param media {@link M}
     * @return {@link Set} of {@link User}s
     */
    Set<User> dislikedBy(M media);

    /**
     * Returns a {@link Set} of {@link M} that the given {@link User} liked
     *
     * @param user {@link User}
     * @return {@link Set} of {@link M}
     */
    Set<M> liked(User user);

    /**
     * Returns a {@link Set} of {@link M} that the given {@link User} disliked
     *
     * @param user {@link User}
     * @return {@link Set} of {@link M}
     */
    Set<M> disliked(User user);
}
