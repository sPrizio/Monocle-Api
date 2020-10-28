package com.priago.monocleapi.api.converters.media;

import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;

import java.util.Collection;

/**
 * A DTO converter for {@link MonocleMedia} & {@link MonocleMediaResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleMediaConverter<M extends MonocleMedia, R extends MonocleMediaResource> extends MonocleConverter<M, R> {

    /**
     * Converts an entity (object stored the database) into a resource (a dto used by facades) with the ability
     * to exclude or include user likes
     *
     * @param media {@link M} to convert
     * @param excludeLikes flag to include likes
     * @param excludeLikedBy flag to include liked by
     * @return newly created resource based off of the entity
     */
    R convert(M media, boolean excludeLikes, boolean excludeLikedBy);

    /**
     * Converts a list of entities into a list of resources
     *
     * @param medias {@link Collection} of {@link M}
     * @param excludeLikes flag to include likes
     * @param excludeLikedBy flag to include liked by
     * @return list of resources based off of their respective entities
     */
    Collection<R> convertAll(Collection<M> medias, boolean excludeLikes, boolean excludeLikedBy);
}
