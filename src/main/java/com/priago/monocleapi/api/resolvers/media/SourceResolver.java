package com.priago.monocleapi.api.resolvers.media;

import com.priago.monocleapi.api.converters.media.SourceConverter;
import com.priago.monocleapi.api.resolvers.MonocleResolver;
import com.priago.monocleapi.api.resources.entities.media.impl.SourceResource;
import com.priago.monocleapi.client.models.data.impl.SourceResponseData;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.services.entities.media.impl.SourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Implementation of {@link MonocleResolver} for {@link SourceResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceResolver")
public class SourceResolver implements MonocleResolver<SourceResponseData, Source, SourceResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceResolver.class);

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "sourceService")
    private SourceService sourceService;


    //  METHODS

    @Override
    public Source resolveEntity(SourceResponseData data, boolean save) {
        if (data == null) {
            LOGGER.error("data was null or empty");
            return null;
        }

        Optional<Source> source = Optional.empty();
        if (StringUtils.isNotEmpty(data.getUrl())) {
            source = this.sourceService.findSourceByUrl(data.getUrl());
        } else if (StringUtils.isNotEmpty(data.getName())) {
            source = this.sourceService.findSourceByName(data.getName());
        }

        if (source.isPresent()) {
            modify(source.get(), data);
            return save ? this.sourceService.save(source.get()) : source.get();
        } else {
            Source newSource = new Source();
            modify(newSource, data);
            return save ? this.sourceService.save(newSource) : newSource;
        }
    }

    @Override
    public SourceResource resolveResource(SourceResponseData data, boolean save) {
        if (data == null) {
            LOGGER.error("data was null or empty");
            return new SourceResource();
        }

        Source source = resolveEntity(data, save);
        if (source != null) {
            return this.sourceConverter.convert(source);
        }

        return null;
    }


    //  HELPERS

    /**
     * Modifies the given {@link Source} with data from the given {@link SourceResponseData}
     *
     * @param source source to update/create
     * @param data data that was fetched
     */
    private void modify(final Source source, final SourceResponseData data) {
        source.setCountry(data.getCountry() != null ? data.getCountry().getCode() : source.getCountry());
        source.setLanguage(data.getLanguage() != null ? data.getLanguage().getCode() : source.getLanguage());
        source.setCategory(data.getCategory() != null ? data.getCategory().getCode() : source.getCategory());
        source.setUrl(StringUtils.isNotEmpty(data.getUrl()) ? data.getUrl() : source.getUrl());
        source.setDescription(StringUtils.isNotEmpty(data.getDescription()) ? data.getDescription() : source.getDescription());
        source.setName(StringUtils.isNotEmpty(data.getName()) ? data.getName() : source.getName());
        source.setId(computedId(source, data));
    }

    /**
     * Computes an id for the given source
     *
     * @param source source to update/create
     * @param data data that was fetched
     * @return a unique id
     */
    private String computedId(final Source source, final SourceResponseData data) {
        if (StringUtils.isNotEmpty(data.getId())) {
            return data.getId();
        } else if (StringUtils.isNotEmpty(data.getName())) {
            return data.getName().toLowerCase().replace(" ", "-").trim();
        } else {
            return source.getId();
        }
    }
}
