package com.priago.monocleapi.core.repositories.like.author;

import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.repositories.like.AbstractMonocleLikeRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Default implementation of {@link AuthorLikeRepositoryCustom}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository
public class AuthorLikeRepositoryImpl extends AbstractMonocleLikeRepository<Author> implements AuthorLikeRepositoryCustom {

    private static final String LIKES_DISLIKES_QUERY = "SELECT COUNT(*) FROM author_like WHERE author_id = :entity AND cardinality = :cardinality";
    private static final String USER_MEDIA_QUERY = "SELECT * FROM author_like, user WHERE author_like.user_id = user.pk AND author_id = :entity AND cardinality = :cardinality ORDER BY author_like.date_time DESC";
    private static final String MEDIA_USER_QUERY = "SELECT * FROM author_like, author WHERE author_like.author_id = author.pk AND user_id = :entity AND cardinality = :cardinality ORDER BY author_like.date_time DESC";


    //  METHODS

    @Override
    public Integer getLikes(Author media) {
        return getCount(LIKES_DISLIKES_QUERY, media, MonocleLikeCardinality.LIKE);
    }

    @Override
    public Integer getDislikes(Author media) {
        return getCount(LIKES_DISLIKES_QUERY, media, MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public Set<User> likedBy(Author media) {
        return getUsers(USER_MEDIA_QUERY, media, MonocleLikeCardinality.LIKE);
    }

    @Override
    public Set<User> dislikedBy(Author media) {
        return getUsers(USER_MEDIA_QUERY, media, MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public Set<Author> liked(User user) {
        return getMedia(MEDIA_USER_QUERY, user, MonocleLikeCardinality.LIKE, Author.class);
    }

    @Override
    public Set<Author> disliked(User user) {
        return getMedia(MEDIA_USER_QUERY, user, MonocleLikeCardinality.DISLIKE, Author.class);
    }
}
