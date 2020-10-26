package com.priago.monocleapi.api.facades;

import com.google.common.collect.Lists;
import com.priago.monocleapi.api.converters.MonocleConverter;
import com.priago.monocleapi.api.models.data.search.MonoclePageAndSortData;
import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.services.entities.MonocleEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic implementation of {@link MonocleEntityFacade}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public abstract class AbstractMonocleEntityFacade<E extends MonocleEntity, R extends MonocleResource, C extends MonocleConverter<E, R>, S extends MonocleEntityService<E>> implements MonocleEntityFacade<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMonocleEntityFacade.class);

    @Autowired
    private C converter;

    @Autowired
    private S service;


    //  METHODS

    @Override
    public R find(String uid) {
        Optional<E> entity = this.service.find(-1L);
        return entity.map(e -> this.converter.convert(e)).orElse(null);
    }

    @Override
    public List<R> findAll(MonoclePageAndSortData data) {
        return Lists.newArrayList(this.converter.convertAll(this.service.findAll(createPageAndSort(data))));
    }

    @Override
    public R create(Map<String, Object> params) {

        E entity = this.service.create(params);

        if (entity != null) {
            return this.converter.convert(entity);
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        this.service.delete(id);
    }


    //  HELPERS

    /**
     * Creates a {@link Pageable} form the given {@link MonoclePageAndSortData}
     *
     * @param data paging and sorting data
     * @return a {@link Pageable} for use with Spring Repositories
     */
    protected Pageable createPageAndSort(MonoclePageAndSortData data) {
        if (data != null && !data.isEmpty()) {
            Sort sort = Sort.by(Sort.Direction.valueOf(data.getSortOrder().getCode().toUpperCase()), data.getAttribute());
            return PageRequest.of(data.getPageNumber(), data.getPageSize(), sort);
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "pk");
        return PageRequest.of(0, 10, sort);
    }
}
