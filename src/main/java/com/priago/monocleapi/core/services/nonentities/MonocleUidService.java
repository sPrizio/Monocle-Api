package com.priago.monocleapi.core.services.nonentities;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.MonocleEntity;

/**
 * Service-layer for computing uid's and pk's
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleUidService<E extends MonocleEntity, R extends  MonocleResource> {

    /**
     * Computes a uid for a {@link R}
     *
     * @param entity {@link E}
     * @return a unique identifier for the {@link R}
     */
    String computeUid(E entity);

    /**
     * Computes the pk for a {@link E} from the {@link R}
     *
     * @param resource {@link R}
     * @return the pk for the {@link E}
     */
    Long computePk(R resource);

    /**
     * Deconstruct the uid into its class
     *
     * @param uid resource's uid
     * @return class name
     */
    String computeClass(String uid);

    /**
     * Computes the class and pk for a {@link E} from the {@link R}
     *
     * @param resource {@link R}
     * @return the pk for the {@link E}
     */
    String compute(R resource);
}
