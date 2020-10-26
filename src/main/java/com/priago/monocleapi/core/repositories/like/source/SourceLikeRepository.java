package com.priago.monocleapi.core.repositories.like.source;

import com.priago.monocleapi.core.models.entities.like.impl.SourceLike;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO access-layer for {@link SourceLike}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("sourceLikeRepository")
public interface SourceLikeRepository extends MonocleRepository, SourceLikeRepositoryCustom, PagingAndSortingRepository<SourceLike, Long> {
}
