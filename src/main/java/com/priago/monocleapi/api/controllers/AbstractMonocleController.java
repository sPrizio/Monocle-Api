package com.priago.monocleapi.api.controllers;

import com.priago.monocleapi.api.models.StandardJsonResponse;
import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.exceptions.MonocleEntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A parent controller for Monocle controllers
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractMonocleController<R extends MonocleResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMonocleController.class);
    protected static final String DEFAULT_PAGE = "0";
    protected static final String DEFAULT_PAGE_SIZE = "10";
    protected static final String DEFAULT_SORT_ATTRIBUTE = "pk";
    protected static final String DEFAULT_SORT_ORDER = "desc";


    //  METHODS

    /**
     * A generic find method that will determine whether the entity could be found
     *
     * @param uid uid of entity
     * @param resource entity as a result of calling find()
     * @return response based on whether the entity was found
     */
    protected StandardJsonResponse findEntity(String uid, R resource) {
        try {
            if (resource.isPresent()) {
                return new StandardJsonResponse(true, resource, StringUtils.EMPTY);
            } else {
                throw new MonocleEntityNotFoundException("The selected entity could not be found.");
            }
        } catch (MonocleEntityNotFoundException e) {
            LOGGER.error("Entity with id {} could not be found", uid);
            return new StandardJsonResponse(false, null, e.getMessage());
        }
    }
}
