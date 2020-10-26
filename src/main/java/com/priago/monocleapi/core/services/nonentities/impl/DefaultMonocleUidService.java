package com.priago.monocleapi.core.services.nonentities.impl;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link MonocleUidService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("monocleUidService")
public class DefaultMonocleUidService implements MonocleUidService {

    //  METHODS

    @Override
    public String computeUid(MonocleEntity entity) {
        //  TODO: implement this
        return null;
    }

    @Override
    public Long computePk(MonocleResource resource) {
        //  TODO: implement this
        return null;
    }
}
