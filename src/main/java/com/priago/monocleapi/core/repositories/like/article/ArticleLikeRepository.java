package com.priago.monocleapi.core.repositories.like.article;

import com.priago.monocleapi.core.models.entities.like.impl.ArticleLike;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO access-layer for {@link ArticleLike}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("articleLikeRepository")
public interface ArticleLikeRepository extends MonocleRepository, ArticleLikeRepositoryCustom, PagingAndSortingRepository<ArticleLike, Long> {
}
