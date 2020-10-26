package com.priago.monocleapi.core.repositories.media.author;

import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO access-layer for {@link Author}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("authorRepository")
public interface AuthorRepository extends MonocleRepository, AuthorRepositoryCustom, PagingAndSortingRepository<Author, Long> {

    /**
     * Attempts to find an {@link Author}'s name excluding the case and using wildcard search
     *
     * @param name author's name
     * @return an {@link Optional} of the possibly found {@link Author}
     */
    Optional<Author> findAuthorByNameContainingIgnoreCase(String name);
}
