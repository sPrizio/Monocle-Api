package com.priago.monocleapi.core.repositories.like;

import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import com.priago.monocleapi.core.models.entities.user.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Parent-level implementation for {@link MonocleLikeRepository}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("abstractMonocleLikeRepository")
public abstract class AbstractMonocleLikeRepository<M extends MonocleMedia> {

    @PersistenceContext
    private EntityManager entityManager;


    //  METHODS

    /**
     * Gets the count of likes/dislikes
     *
     * @param queryString SQL query
     * @param media {@link M}
     * @param cardinality {@link MonocleLikeCardinality} (like/dislike)
     * @return integer count
     */
    protected int getCount(String queryString, M media, MonocleLikeCardinality cardinality) {
        Query query = this.entityManager.createNativeQuery(queryString);
        query.setParameter(MonocleCoreConstants.Database.ENTITY, media.getPk());
        query.setParameter(MonocleCoreConstants.Database.CARDINALITY, cardinality);
        return ((Number) query.getSingleResult()).intValue();
    }

    /**
     * Gets a {@link Set} of {@link User}s that liked/disliked the given {@link M}
     *
     * @param queryString SQL query
     * @param media {@link M}
     * @param cardinality {@link MonocleLikeCardinality} (like/dislike)
     * @return {@link Set} of {@link User}
     */
    protected Set<User> getUsers(String queryString, M media, MonocleLikeCardinality cardinality) {
        Query query = this.entityManager.createNativeQuery(queryString, User.class);
        query.setParameter(MonocleCoreConstants.Database.ENTITY, media.getPk());
        query.setParameter(MonocleCoreConstants.Database.CARDINALITY, cardinality.getCode());
        return new LinkedHashSet<>((List<User>) query.getResultList());
    }

    /**
     * Gets a {@link Set} of {@link M} that the given {@link User} liked/disliked
     *
     * @param queryString SQL query
     * @param user {@link User}
     * @param cardinality {@link MonocleLikeCardinality} (like/dislike)
     * @param clazz casted class
     * @return {@link Set} of {@link M}
     */
    protected Set<M> getMedia(String queryString, User user, MonocleLikeCardinality cardinality, Class<?> clazz) {
        Query query = this.entityManager.createNativeQuery(queryString, clazz);
        query.setParameter(MonocleCoreConstants.Database.ENTITY, user.getPk());
        query.setParameter(MonocleCoreConstants.Database.CARDINALITY, cardinality);
        return new LinkedHashSet<>((List <M>) query.getResultList());
    }
}
