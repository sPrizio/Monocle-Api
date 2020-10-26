package com.priago.monocleapi.api.facades.media;

import com.priago.monocleapi.api.facades.MonocleEntityFacade;
import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;

import java.util.Set;

/**
 * Defines the facade-layer architecture for {@link MonocleMediaResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleMediaFacade<M extends MonocleMediaResource> extends MonocleEntityFacade<M> {

    /**
     * Returns a {@link Set} of {@link UserResource}s that liked the given {@link M}
     *
     * @param media {@link M}
     * @return {@link Set} of {@link UserResource}s
     */
    Set<UserResource> likedBy(M media);

    /**
     * Returns a {@link Set} of {@link UserResource}s that dislike the given {@link M}
     *
     * @param media {@link M}
     * @return {@link Set} of {@link UserResource}s
     */
    Set<UserResource> dislikedBy(M media);

    /**
     * Returns a {@link Set} of {@link M} that the given {@link UserResource} liked
     *
     * @param user {@link UserResource}
     * @return {@link Set} of {@link M}
     */
    Set<M> liked(UserResource user);

    /**
     * Returns a {@link Set} of {@link M} that the given {@link UserResource} disliked
     *
     * @param user {@link UserResource}
     * @return {@link Set} of {@link M}
     */
    Set<M> disliked(UserResource user);
}
