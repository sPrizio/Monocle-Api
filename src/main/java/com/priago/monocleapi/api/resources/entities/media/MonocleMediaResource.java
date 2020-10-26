package com.priago.monocleapi.api.resources.entities.media;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;

/**
 * DTO architecture for {@link MonocleMedia}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleMediaResource extends MonocleResource {

    /**
     * Returns the number of positive likes
     *
     * @return number of positive likes
     */
    int getLikes();

    /**
     * Returns the number of negative likes
     *
     * @return number of negative likes
     */
    int getDislikes();
}
