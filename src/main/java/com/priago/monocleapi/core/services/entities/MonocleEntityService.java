package com.priago.monocleapi.core.services.entities;

import com.priago.monocleapi.core.models.entities.MonocleEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Defines the service-layer architecture for {@link MonocleEntity}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleEntityService<E extends MonocleEntity> {

    /**
     * Updates an entity's information (performs a db lookup for the most up-to-date information)
     *
     * @param entity entity that we wish to update
     */
    void refresh(E entity);

    /**
     * Finds an entity with the given id. We use optional here since it can happen that the given id does not match any entity
     *
     * @param id entity id
     * @return optional, if the value is not found the optional will be empty
     */
    Optional<E> find(Long id);

    /**
     * Finds all entities
     *
     * @return a list of all entities
     */
    List<E> findAll(Pageable pageable);

    /**
     * Saves an entity to the db. Inserts or updates it via db calls
     *
     * @param entity entity that we're creating or updating
     * @return newly updated/created entity
     */
    E save(E entity);

    /**
     * Deletes an entity with the given id. We attempt to find the entity with the id and if found, remove it from the db
     *
     * @param id entity id
     */
    void delete(Long id);

    /**
     * Creates a new entity with the supplied parameters. Usually used in conjunction with save()
     *
     * @param params insertion parameters for the new entity
     * @return newly created entity
     */
    E create(Map<String, Object> params);
}
