package com.priago.monocleapi.core.services.entities;

import com.google.common.collect.Lists;
import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import com.priago.monocleapi.core.translators.MonocleTranslator;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic implementation of {@link MonocleEntityService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractMonocleEntityService<E extends MonocleEntity, R extends PagingAndSortingRepository<E, Long>, T extends MonocleTranslator<E>> implements MonocleEntityService<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMonocleEntityService.class);

    @Resource(name = "monocleRepository")
    private MonocleRepository monocleRepository;

    @Autowired
    private R repository;

    @Autowired
    private T translator;


    //  METHODS

    @Override
    public void refresh(E entity) {
        if (entity != null) {
            this.monocleRepository.refresh(entity);
        } else {
            LOGGER.error("entity was null or empty");
        }
    }

    @Override
    public Optional<E> find(Long id) {
        if (id != null) {
            return this.repository.findById(id);
        }

        LOGGER.error("id was null or empty");
        return Optional.empty();
    }

    @Override
    public List<E> findAll(Pageable pageable) {
        return Lists.newArrayList(this.repository.findAll(pageable));
    }

    @Override
    public E save(E entity) {
        if (entity != null) {
            return this.repository.save(entity);
        }

        LOGGER.error("entity was null or empty");
        return null;
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            find(id).ifPresent(e -> this.repository.deleteById(e.getPk()));
        } else {
            LOGGER.error("id was null or empty");
        }
    }

    @Override
    public E create(Map<String, Object> params) {
        if (MapUtils.isNotEmpty(params)) {
            E entity = this.translator.translate(params);

            if (entity != null) {
                return this.repository.save(entity);
            } else {
                LOGGER.error("entity could not be created");
            }
        } else {
            LOGGER.error("params were null or missing");
        }

        return null;
    }
}
