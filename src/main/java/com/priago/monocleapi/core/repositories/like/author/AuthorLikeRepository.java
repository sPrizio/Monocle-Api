package com.priago.monocleapi.core.repositories.like.author;

import com.priago.monocleapi.core.models.entities.like.impl.AuthorLike;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO access-layer for {@link AuthorLike}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("authorLikeRepository")
public interface AuthorLikeRepository extends MonocleRepository, AuthorLikeRepositoryCustom, PagingAndSortingRepository<AuthorLike, Long> {
}
