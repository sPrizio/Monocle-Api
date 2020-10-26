package com.priago.monocleapi.api.resolvers;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.client.models.data.MonocleResponseData;
import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.services.entities.MonocleEntityService;

/**
 * A resolver is a class that aims to transmute {@link MonocleResponseData} into {@link MonocleResource} through the {@link MonocleEntityService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleResolver<D extends MonocleResponseData, E extends MonocleEntity, R extends MonocleResource> {

    /**
     * Resolves the given {@link MonocleResponseData} into a {@link MonocleEntity}
     *
     * @param data data to resolve
     * @return a {@link MonocleEntity}
     */
    E resolveEntity(D data, boolean save);

    /**
     * Resolves the given {@link MonocleResponseData} into a {@link MonocleResource}
     *
     * @param data data to resolve
     * @return a {@link MonocleResource}
     */
    R resolveResource(D data, boolean save);
}
