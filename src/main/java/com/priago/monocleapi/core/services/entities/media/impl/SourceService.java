package com.priago.monocleapi.core.services.entities.media.impl;

import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.repositories.like.source.SourceLikeRepository;
import com.priago.monocleapi.core.repositories.media.source.SourceRepository;
import com.priago.monocleapi.core.services.entities.media.AbstractMonocleMediaService;
import com.priago.monocleapi.core.services.entities.media.MonocleMediaService;
import com.priago.monocleapi.core.translators.media.SourceTranslator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Default implementation of the service-layer for {@link Source} entities
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("sourceService")
public class SourceService extends AbstractMonocleMediaService<Source, SourceLikeRepository, SourceRepository, SourceTranslator> implements MonocleMediaService<Source> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceService.class);

    @Resource(name = "sourceRepository")
    private SourceRepository sourceRepository;


    //  METHODS

    /**
     * Attempts to find a {@link Source} by its name
     *
     * @param name source's name
     * @return an {@link Optional} if the {@link Source} is found
     */
    public Optional<Source> findSourceByName(String name) {

        if (StringUtils.isEmpty(name)) {
            LOGGER.error("name was null or empty");
            return Optional.empty();
        }

        return this.sourceRepository.findSourceByNameContainingIgnoreCase(name);
    }

    /**
     * Attempts to find a {@link Source} by its url
     * @param url source's url
     * @return an {@link Optional} if the {@link Source} is found
     */
    public Optional<Source> findSourceByUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            LOGGER.error("url was null or empty");
            return Optional.empty();
        }

        return this.sourceRepository.findSourceByUrl(url);
    }
}
