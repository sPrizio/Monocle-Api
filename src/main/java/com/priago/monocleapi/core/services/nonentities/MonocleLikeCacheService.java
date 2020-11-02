package com.priago.monocleapi.core.services.nonentities;

import com.priago.monocleapi.core.enums.MonocleLikeCardinality;

import java.util.List;

/**
 * Caching service for handling likes/dislikes in Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleLikeCacheService {

    /**
     * Obtains the count of likes/dislikes for the given media uid
     *
     * @param mediaUid media's uid
     * @param cardinality like/dislike
     * @return integer count of likes/dislikes
     */
    int getCount(String mediaUid, MonocleLikeCardinality cardinality);

    /**
     * Get list of user's uids who liked the given media
     *
     * @param mediaUid media's uid
     * @param cardinality like/dislike
     * @return list of user uids
     */
    List<String> getLikes(String mediaUid, MonocleLikeCardinality cardinality);

    /**
     * Get a list of the liked/disliked medias for the given user uid
     *
     * @param userUid user's uid
     * @param cacheId media id to fetch
     * @return list of media uids
     */
    List<String> get(String userUid, String cacheId);

    /**
     * Adds a media to the cache
     *
     * @param userUid user's uid
     * @param mediaUid media's uid
     * @param cardinality like/dislike
     */
    void add(String userUid, String mediaUid, MonocleLikeCardinality cardinality);

    /**
     * Removes a media from the cache
     *
     * @param userUid user's uid
     * @param mediaUid media's uid
     * @param cardinality like/dislike
     */
    void remove(String userUid, String mediaUid, MonocleLikeCardinality cardinality);
}
