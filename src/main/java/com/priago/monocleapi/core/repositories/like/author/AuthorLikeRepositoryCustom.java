package com.priago.monocleapi.core.repositories.like.author;

import com.priago.monocleapi.core.models.entities.like.impl.AuthorLike;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.repositories.like.MonocleLikeRepository;
import org.springframework.stereotype.Repository;

/**
 * Custom repository methods for {@link AuthorLike}. We define custom methods for functionality that cannot be handled by the OOTB spring repository system
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository
public interface AuthorLikeRepositoryCustom extends MonocleLikeRepository<Author> {
}
