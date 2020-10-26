package com.priago.monocleapi.core.repositories.like.source;

import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.repositories.like.AbstractMonocleLikeRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Default implementation of {@link SourceLikeRepositoryCustom}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository
public class SourceLikeRepositoryImpl extends AbstractMonocleLikeRepository<Source> implements SourceLikeRepositoryCustom {

    private static final String LIKES_DISLIKES_QUERY = "SELECT COUNT(*) FROM source_like WHERE source_id = :entity AND cardinality = :cardinality";
    private static final String USER_MEDIA_QUERY = "SELECT * FROM source_like, user WHERE source_like.user_id = user.pk AND source_id = :entity AND cardinality = :cardinality ORDER BY source_like.date_time DESC";
    private static final String MEDIA_USER_QUERY = "SELECT * FROM source_like, source WHERE source_like.source_id = source.pk AND user_id = :entity AND cardinality = :cardinality ORDER BY source_like.date_time DESC";


    //  METHODS

    @Override
    public Integer getLikes(Source media) {
        return getCount(LIKES_DISLIKES_QUERY, media, MonocleLikeCardinality.LIKE);
    }

    @Override
    public Integer getDislikes(Source media) {
        return getCount(LIKES_DISLIKES_QUERY, media, MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public Set<User> likedBy(Source media) {
        return getUsers(USER_MEDIA_QUERY, media, MonocleLikeCardinality.LIKE);
    }

    @Override
    public Set<User> dislikedBy(Source media) {
        return getUsers(USER_MEDIA_QUERY, media, MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public Set<Source> liked(User user) {
        return getMedia(MEDIA_USER_QUERY, user, MonocleLikeCardinality.LIKE, Source.class);
    }

    @Override
    public Set<Source> disliked(User user) {
        return getMedia(MEDIA_USER_QUERY, user, MonocleLikeCardinality.DISLIKE, Source.class);
    }
}
