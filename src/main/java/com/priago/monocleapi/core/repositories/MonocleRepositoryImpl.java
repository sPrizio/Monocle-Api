package com.priago.monocleapi.core.repositories;

import com.priago.monocleapi.core.models.entities.MonocleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Default implementation of {@link MonocleRepository}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository("monocleRepository")
public class MonocleRepositoryImpl implements MonocleRepository {

    @PersistenceContext
    private EntityManager entityManager;


    //  METHODS

    @Override
    public void refresh(MonocleEntity entity) {
        this.entityManager.refresh(this.entityManager.merge(entity));
    }
}
