package com.priago.monocleapi.core.repositories;

import com.priago.monocleapi.core.models.entities.MonocleEntity;

/**
 * Parent-level repository structure for all {@link MonocleEntity} objects
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleRepository {

    /**
     * Refreshes an entity, meaning to update all of its information and relations
     *
     * @param entity entity that we want to have its data refreshed
     */
    void refresh(MonocleEntity entity);
}
