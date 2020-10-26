package com.priago.monocleapi.core.repositories.media.article;

import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO access-layer for {@link Article}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("articleRepository")
public interface ArticleRepository extends MonocleRepository, ArticleRepositoryCustom, PagingAndSortingRepository<Article, Long> {

    /**
     * Attempts to find {@link Article}s by their {@link Author}
     *
     * @param author article's author
     * @return a {@link List} of {@link Article}s with the given {@link Author}
     */
    List<Article> findArticlesByAuthor(Author author, Pageable pageable);

    /**
     * Attempts to find {@link Article}s by their {@link Source}
     *
     * @param source article's source
     * @return a {@link List} of {@link Article}s with the given {@link Source}
     */
    List<Article> findArticlesBySource(Source source, Pageable pageable);

    /**
     * Attempts to find an {@link Article} with the matching url
     *
     * @param url article's url
     * @return an {@link Optional} containing an {@link Article}
     */
    Optional<Article> findArticleByUrl(String url);

    /**
     * Attempts to lookup an {@link Article} by its title
     *
     * @param title article's title
     * @return a {@link List} containing {@link Article}s with a title matching somewhat similarly to the given title
     */
    List<Article> findArticlesByTitleContainingIgnoreCase(String title, Pageable pageable);
}
