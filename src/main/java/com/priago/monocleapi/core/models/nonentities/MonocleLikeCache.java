package com.priago.monocleapi.core.models.nonentities;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.like.MonocleLike;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representation of a cache for {@link MonocleLike}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class MonocleLikeCache {

    private Map<String, Map<String, Set<MonocleCacheLike>>> cache;


    //  METHODS

    /**
     * Obtain a count of likes/dislikes for the given article uid
     *
     * @param articleUid article's uid
     * @param cardinality like/dislike
     * @return integer count
     */
    public int getArticleCount(String articleUid, MonocleLikeCardinality cardinality) {
        return getCount(MonocleCoreConstants.Cache.ARTICLE_KEY, articleUid, cardinality);
    }

    /**
     * Obtain a count of likes/dislikes for the given author uid
     *
     * @param authorUid author's uid
     * @param cardinality like/dislike
     * @return integer count
     */
    public int getAuthorCount(String authorUid, MonocleLikeCardinality cardinality) {
        return getCount(MonocleCoreConstants.Cache.AUTHOR_KEY, authorUid, cardinality);
    }

    /**
     * Obtain a count of likes/dislikes for the given source uid
     *
     * @param sourceUid source's uid
     * @param cardinality like/dislike
     * @return integer count
     */
    public int getSourceCount(String sourceUid, MonocleLikeCardinality cardinality) {
        return getCount(MonocleCoreConstants.Cache.SOURCE_KEY, sourceUid, cardinality);
    }

    /**
     * Obtain a list of users who liked the given article
     *
     * @param articleUid article's uid
     * @param cardinality like/dislike
     * @return list of user uids
     */
    public List<String> getArticleLikes(String articleUid, MonocleLikeCardinality cardinality) {
        return getLikes(MonocleCoreConstants.Cache.ARTICLE_KEY, articleUid, cardinality);
    }

    /**
     * Obtain a list of users who liked the given author
     *
     * @param authorUid author's uid
     * @param cardinality like/dislike
     * @return list of user uids
     */
    public List<String> getAuthorLikes(String authorUid, MonocleLikeCardinality cardinality) {
        return getLikes(MonocleCoreConstants.Cache.AUTHOR_KEY, authorUid, cardinality);
    }

    /**
     * Obtain a list of users who liked the given source
     *
     * @param sourceUid source's uid
     * @param cardinality like/dislike
     * @return list of user uids
     */
    public List<String> getSourceLikes(String sourceUid, MonocleLikeCardinality cardinality) {
        return getLikes(MonocleCoreConstants.Cache.SOURCE_KEY, sourceUid, cardinality);
    }

    /**
     * Gets articles liked/disliked by the user
     *
     * @param userUid user's uid
     * @return list of article uids
     */
    public List<String> getArticles(String userUid) {
        return get(MonocleCoreConstants.Cache.ARTICLE_KEY, userUid);
    }

    /**
     * Gets authors liked/disliked by the user
     *
     * @param userUid user's uid
     * @return list of author uids
     */
    public List<String> getAuthors(String userUid) {
        return get(MonocleCoreConstants.Cache.AUTHOR_KEY, userUid);
    }

    /**
     * Gets sources liked/disliked by the user
     *
     * @param userUid user's uid
     * @return list of source uids
     */
    public List<String> getSources(String userUid) {
        return get(MonocleCoreConstants.Cache.SOURCE_KEY, userUid);
    }

    /**
     * Adds an {@link Article} to the cache
     *
     * @param userUid user's uid
     * @param articleUid article's uid
     * @param cardinality like/dislike
     */
    public void addArticle(String userUid, String articleUid, MonocleLikeCardinality cardinality) {
        safeAdd(MonocleCoreConstants.Cache.ARTICLE_KEY, userUid, articleUid, cardinality);
    }

    /**
     * Adds an {@link Author} to the cache
     *
     * @param userUid user's uid
     * @param authorUid author's uid
     * @param cardinality like/dislike
     */
    public void addAuthor(String userUid, String authorUid, MonocleLikeCardinality cardinality) {
        safeAdd(MonocleCoreConstants.Cache.AUTHOR_KEY, userUid, authorUid, cardinality);
    }

    /**
     * Adds a {@link Source} to the cache
     *
     * @param userUid user's uid
     * @param sourceUid source's uid
     * @param cardinality like/dislike
     */
    public void addSource(String userUid, String sourceUid, MonocleLikeCardinality cardinality) {
        safeAdd(MonocleCoreConstants.Cache.SOURCE_KEY, userUid, sourceUid, cardinality);
    }

    /**
     * Removes an {@link Article} from the cache
     *
     * @param userUid user's uid
     * @param articleUid article's uid
     * @param cardinality like/dislike
     */
    public void removeArticle(String userUid, String articleUid, MonocleLikeCardinality cardinality) {
        safeRemove(MonocleCoreConstants.Cache.ARTICLE_KEY, userUid, articleUid, cardinality);
    }

    /**
     * Removes an {@link Author} from the cache
     *
     * @param userUid user's uid
     * @param authorUid author's uid
     * @param cardinality like/dislike
     */
    public void removeAuthor(String userUid, String authorUid, MonocleLikeCardinality cardinality) {
        safeRemove(MonocleCoreConstants.Cache.AUTHOR_KEY, userUid, authorUid, cardinality);
    }

    /**
     * Removes a {@link Source} from the cache
     *
     * @param userUid user's uid
     * @param sourceUid source's uid
     * @param cardinality like/dislike
     */
    public void removeSource(String userUid, String sourceUid, MonocleLikeCardinality cardinality) {
        safeRemove(MonocleCoreConstants.Cache.SOURCE_KEY, userUid, sourceUid, cardinality);
    }


    //  HELPERS

    /**
     * Safely adds a user's like to the cache
     *
     * @param key entity key, used to determine where to store the like
     * @param userUid user's uid
     * @param likeUid uid of the entity being liked
     * @param cardinality like/dislike
     */
    private void safeAdd(String key, String userUid, String likeUid, MonocleLikeCardinality cardinality) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(userUid) && StringUtils.isNotEmpty(likeUid) && cardinality != null) {
            Map<String, Set<MonocleCacheLike>> likeMap;
            if (MapUtils.isNotEmpty(this.cache)) {
                likeMap = this.cache.getOrDefault(userUid, new HashMap<>());
            } else {
                this.cache = new HashMap<>();
                likeMap = new HashMap<>();
            }

            Set<MonocleCacheLike> likes;
            if (MapUtils.isNotEmpty(likeMap)) {
                likes = likeMap.getOrDefault(key, new HashSet<>());
            } else {
                likes = new HashSet<>();
            }

            MonocleCacheLike like = new MonocleCacheLike(likeUid, cardinality.getCode());
            if (!likes.add(like)) {
                likes.remove(like);
                likes.add(like);
            }

            likeMap.put(key, likes);
            this.cache.put(userUid, likeMap);
        }
    }

    /**
     * Safely removes a user's like from the cache
     *
     * @param key entity key, used to determine where to remove the like
     * @param userUid user's uid
     * @param likeUid uid of the entity being liked
     * @param cardinality like/dislike
     */
    private void safeRemove(String key, String userUid, String likeUid, MonocleLikeCardinality cardinality) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(userUid) && StringUtils.isNotEmpty(likeUid) && cardinality != null) {
            Map<String, Set<MonocleCacheLike>> likeMap;
            if (MapUtils.isNotEmpty(this.cache)) {
                likeMap = this.cache.getOrDefault(userUid, new HashMap<>());
            } else {
                return;
            }

            Set<MonocleCacheLike> likes;
            if (MapUtils.isNotEmpty(likeMap)) {
                likes = likeMap.getOrDefault(key, new HashSet<>());
            } else {
                return;
            }

            MonocleCacheLike like = new MonocleCacheLike(likeUid, cardinality.getCode());
            if (CollectionUtils.isNotEmpty(likes)) {
                likes.remove(like);
            }

            likeMap.put(key, likes);
            this.cache.put(userUid, likeMap);
        }
    }

    /**
     * Returns a list of user uids that liked the given entity
     *
     * @param key entity key
     * @param uid entity uid
     * @param cardinality like/dislike
     * @return list of user uids
     */
    private List<String> getLikes(String key, String uid, MonocleLikeCardinality cardinality) {

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(uid) || cardinality == null) {
            return Collections.emptyList();
        }

        if (MapUtils.isEmpty(this.cache)) {
            return Collections.emptyList();
        }

        return this.cache.entrySet()
                .stream()
                .filter(e -> MapUtils.isNotEmpty(e.getValue()))
                .filter(e -> e.getValue().containsKey(key))
                .filter(e -> e.getValue().get(key).contains(new MonocleCacheLike(uid, cardinality.getCode())))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of entity uids for the given user
     *
     * @param key entity that should be fetched
     * @param userUid user's uid
     * @return list of entity uids
     */
    private List<String> get(String key, String userUid) {

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(userUid)) {
            return Collections.emptyList();
        }

        if (MapUtils.isEmpty(this.cache)) {
            return Collections.emptyList();
        }

        Map<String, Set<MonocleCacheLike>> map = this.cache.getOrDefault(userUid, new HashMap<>());
        if (MapUtils.isEmpty(map)) {
            return Collections.emptyList();
        }

        List<String> res = new ArrayList<>();
        if (map.containsKey(key)) {
            return map.get(key).stream().map(MonocleCacheLike::getEntityUid).collect(Collectors.toList());
        }

        return res;
    }

    /**
     * Returns the sum of likes/dislikes for the given entity uid
     *
     * @param key entity key
     * @param uid entity uid
     * @param cardinality like/dislike
     * @return integer count of likes/dislikes
     */
    private int getCount(String key, String uid, MonocleLikeCardinality cardinality) {

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(uid) || cardinality == null) {
            return -1;
        }

        if (MapUtils.isEmpty(this.cache)) {
            return -1;
        }

        return getLikes(key, uid, cardinality).size();
    }


    //  NESTED CLASSES

    /**
     * Class representation of a cache like, storing the entity and the cardinality
     */
    @RequiredArgsConstructor
    private static class MonocleCacheLike {

        @Getter
        @NonNull
        private final String entityUid;

        @Getter
        @NonNull
        private final String cardinality;


        //  METHODS

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MonocleCacheLike that = (MonocleCacheLike) o;
            return this.entityUid.equals(that.entityUid) && this.cardinality.equals(that.cardinality);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.entityUid);
        }
    }
}
