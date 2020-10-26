package com.priago.monocleapi.core.repositories.like.article;

import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.repositories.like.AbstractMonocleLikeRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Default implementation of {@link ArticleLikeRepositoryCustom}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository
public class ArticleLikeRepositoryImpl extends AbstractMonocleLikeRepository<Article> implements ArticleLikeRepositoryCustom {

    private static final String LIKES_DISLIKES_QUERY = "SELECT COUNT(*) FROM article_like WHERE article_id = :entity AND cardinality = :cardinality";
    private static final String USER_MEDIA_QUERY = "SELECT * FROM article_like, user WHERE article_like.user_id = user.pk AND article_id = :entity AND cardinality = :cardinality ORDER BY article_like.date_time DESC";
    private static final String MEDIA_USER_QUERY = "SELECT * FROM article_like, article WHERE article_like.article_id = article.pk AND user_id = :entity AND cardinality = :cardinality ORDER BY article_like.date_time DESC";


    //  METHODS

    @Override
    public Integer getLikes(Article media) {
        return getCount(LIKES_DISLIKES_QUERY, media, MonocleLikeCardinality.LIKE);
    }

    @Override
    public Integer getDislikes(Article media) {
        return getCount(LIKES_DISLIKES_QUERY, media, MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public Set<User> likedBy(Article media) {
        return getUsers(USER_MEDIA_QUERY, media, MonocleLikeCardinality.LIKE);
    }

    @Override
    public Set<User> dislikedBy(Article media) {
        return getUsers(USER_MEDIA_QUERY, media, MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public Set<Article> liked(User user) {
        return getMedia(MEDIA_USER_QUERY, user, MonocleLikeCardinality.LIKE, Article.class);
    }

    @Override
    public Set<Article> disliked(User user) {
        return getMedia(MEDIA_USER_QUERY, user, MonocleLikeCardinality.DISLIKE, Article.class);
    }
}
