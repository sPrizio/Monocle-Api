package com.priago.monocleapi.core.services.nonentities;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.MonocleEntity;

/**
 * Service-layer for computing uid's and pk's
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public interface MonocleUidService {

    String computeUid(MonocleEntity entity);

    Long computePk(MonocleResource resource);
}
