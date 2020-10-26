package com.priago.monocleapi.core.models.entities.media;

import com.priago.monocleapi.core.models.entities.MonocleEntity;

import java.util.List;

/**
 * Class representation of media, public information that Monocle will aim to expose
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleMedia extends MonocleEntity {

    /**
     * Returns a {@link List} of all attribute names that can be sorted
     *
     * @return {@link List} of {@link String}s
     */
    List<String> getSortableAttributes();
}
