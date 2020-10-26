package com.priago.monocleapi.api.facades.media.impl;

import com.priago.monocleapi.api.converters.media.SourceConverter;
import com.priago.monocleapi.api.facades.AbstractMonocleEntityFacade;
import com.priago.monocleapi.api.facades.MonocleEntityFacade;
import com.priago.monocleapi.api.facades.media.AbstractMonocleMediaFacade;
import com.priago.monocleapi.api.facades.media.MonocleMediaFacade;
import com.priago.monocleapi.api.resources.entities.media.impl.SourceResource;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.services.entities.media.impl.SourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Facade implementation for {@link SourceResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Component("sourceFacade")
public class SourceFacade extends AbstractMonocleMediaFacade<Source, SourceResource, SourceConverter, SourceService> implements MonocleMediaFacade<SourceResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceFacade.class);

    @Resource(name = "sourceConverter")
    private SourceConverter sourceConverter;

    @Resource(name = "sourceService")
    private SourceService sourceService;


    //  METHODS

    /**
     * Find an {@link SourceResource} by their name
     *
     * @param name name of the source
     * @return a {@link SourceResource}
     */
    public SourceResource findSourceByName(String name) {
        if (StringUtils.isEmpty(name)) {
            LOGGER.error("name was null or empty");
            return new SourceResource();
        }

        Optional<Source> source = this.sourceService.findSourceByName(name);
        if (source.isPresent()) {
            return this.sourceConverter.convert(source.get());
        }

        LOGGER.error("No source found for name {}", name);
        return new SourceResource();
    }
}
