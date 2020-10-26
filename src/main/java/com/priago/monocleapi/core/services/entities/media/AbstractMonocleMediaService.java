package com.priago.monocleapi.core.services.entities.media;

import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.repositories.like.MonocleLikeRepository;
import com.priago.monocleapi.core.services.entities.AbstractMonocleEntityService;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collections;
import java.util.Set;

/**
 * GenericImplementation of {@link MonocleMediaService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractMonocleMediaService<M extends MonocleMedia, L extends MonocleLikeRepository<M>, R extends PagingAndSortingRepository<M, Long>, T extends MonocleTranslator<M>> extends AbstractMonocleEntityService<M, R, T> implements MonocleMediaService<M> {

    @Autowired
    private L repository;


    //  METHODS

    @Override
    public int getLikes(M media) {
        if (media != null) {
            return this.repository.getLikes(media);
        }

        return -1;
    }

    @Override
    public int getDislikes(M media) {
        if (media != null) {
            return this.repository.getDislikes(media);
        }

        return -1;
    }

    @Override
    public Set<User> likedBy(M media) {
        if (media == null) {
            return Collections.emptySet();
        }

        return this.repository.likedBy(media);
    }

    @Override
    public Set<User> dislikedBy(M media) {
        if (media == null) {
            return Collections.emptySet();
        }

        return this.repository.dislikedBy(media);
    }

    @Override
    public Set<M> liked(User user) {
        if (user == null) {
            return Collections.emptySet();
        }

        return this.repository.liked(user);
    }

    @Override
    public Set<M> disliked(User user) {
        if (user == null) {
            return Collections.emptySet();
        }

        return this.repository.disliked(user);
    }
}
