package com.priago.monocleapi.core.repositories.media.source;

import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO access-layer for {@link Source}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("sourceRepository")
public interface SourceRepository extends MonocleRepository, SourceRepositoryCustom, PagingAndSortingRepository<Source, Long> {

    /**
     * Attempts to find a {@link Source}'s name excluding the case and using wildcard search
     *
     * @param name source's name
     * @return an {@link Optional} of the possibly found {@link Source}
     */
    Optional<Source> findSourceByNameContainingIgnoreCase(String name);

    /**
     * Attempts to find a {@link Source} for the given url
     *
     * @param url source's url
     * @return an {@link Optional} of the possibly found {@link Source}
     */
    Optional<Source> findSourceByUrl(String url);
}
