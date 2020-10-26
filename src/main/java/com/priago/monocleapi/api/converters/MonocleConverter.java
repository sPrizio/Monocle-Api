package com.priago.monocleapi.api.converters;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.MonocleEntity;

import java.util.Collection;

/**
 * A converter is a class that converts entities into DTOs for use by API controllers
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleConverter<E extends MonocleEntity, R extends MonocleResource> {

    /**
     * Converts an entity (object stored the database) into a resource (a dto used by facades)
     *
     * @param entity entity to convert
     * @return newly created resource based off of the entity
     */
    R convert(E entity);

    /**
     * Converts a list of entities into a list of resources
     *
     * @param entities list of entities to convert
     * @return list of resources based off of their respective entities
     */
    Collection<R> convertAll(Collection<E> entities);
}
