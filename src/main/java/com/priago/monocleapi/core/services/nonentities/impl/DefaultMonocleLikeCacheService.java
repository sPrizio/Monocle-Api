package com.priago.monocleapi.core.services.nonentities.impl;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.core.constants.MonocleCoreConstants;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import com.priago.monocleapi.core.models.nonentities.MonocleLikeCache;
import com.priago.monocleapi.core.services.nonentities.MonocleLikeCacheService;
import com.priago.monocleapi.core.services.nonentities.MonocleUidService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of {@link MonocleLikeCacheService}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Service("monocleLikeCacheService")
public class DefaultMonocleLikeCacheService implements MonocleLikeCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMonocleLikeCacheService.class);
    private static final MonocleLikeCache CACHE = new MonocleLikeCache();
    private static final String MEDIA_INVALID = "media was not a valid type";

    @Resource(name = "monocleUidService")
    private MonocleUidService<MonocleMedia, MonocleResource> monocleUidService;


    //  METHODS

    @Override
    public int getCount(String mediaUid, MonocleLikeCardinality cardinality) {

        if (StringUtils.isEmpty(mediaUid) || cardinality == null) {
            LOGGER.error("One or more of the required params was null or empty. mediaUid {}, cardinality {}", mediaUid, cardinality);
            return -1;
        }

        String name = this.monocleUidService.computeClass(mediaUid);
        if (name.equals(Article.class.getSimpleName())) {
            return CACHE.getArticleCount(mediaUid, cardinality);
        } else if (name.equals(Author.class.getSimpleName())) {
            return CACHE.getAuthorCount(mediaUid, cardinality);
        } else if (name.equals(Source.class.getSimpleName())) {
            return CACHE.getSourceCount(mediaUid, cardinality);
        } else {
            LOGGER.error(MEDIA_INVALID);
            return -1;
        }
    }

    @Override
    public List<String> getLikes(String mediaUid, MonocleLikeCardinality cardinality) {

        if (StringUtils.isEmpty(mediaUid)) {
            LOGGER.error("mediaUid was nul or empty");
            return Collections.emptyList();
        }

        String name = this.monocleUidService.computeClass(mediaUid);
        if (name.equals(Article.class.getSimpleName())) {
            return CACHE.getArticleLikes(mediaUid, cardinality);
        } else if (name.equals(Author.class.getSimpleName())) {
            return CACHE.getAuthorLikes(mediaUid, cardinality);
        } else if (name.equals(Source.class.getSimpleName())) {
            return CACHE.getSourceLikes(mediaUid, cardinality);
        } else {
            LOGGER.error(MEDIA_INVALID);
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> get(String userUid, String cacheId) {

        if (StringUtils.isEmpty(userUid) || StringUtils.isEmpty(cacheId)) {
            LOGGER.error("One or more of the required params was null or empty. userUid {}, cacheId {}", userUid, cacheId);
            return Collections.emptyList();
        }

        switch (cacheId) {
            case MonocleCoreConstants.Cache.ARTICLE_KEY:
                return CACHE.getArticles(userUid);
            case MonocleCoreConstants.Cache.AUTHOR_KEY:
                return CACHE.getAuthors(userUid);
            case MonocleCoreConstants.Cache.SOURCE_KEY:
                return CACHE.getSources(userUid);
            default:
                LOGGER.error(MEDIA_INVALID);
                return Collections.emptyList();
        }
    }

    @Override
    public void add(String userUid, String mediaUid, MonocleLikeCardinality cardinality) {
        if (StringUtils.isEmpty(userUid) || StringUtils.isEmpty(mediaUid) || cardinality == null) {
            LOGGER.error("One or more of the required params was null or empty. userUid {}, mediaUid {}, cardinality {}", userUid, mediaUid, cardinality);
            return;
        }

        String name = this.monocleUidService.computeClass(mediaUid);
        if (name.equals(Article.class.getSimpleName())) {
            CACHE.addArticle(userUid, mediaUid, cardinality);
        } else if (name.equals(Author.class.getSimpleName())) {
            CACHE.addAuthor(userUid, mediaUid, cardinality);
        } else if (name.equals(Source.class.getSimpleName())) {
            CACHE.addSource(userUid, mediaUid, cardinality);
        } else {
            LOGGER.error(MEDIA_INVALID);
        }
    }

    @Override
    public void remove(String userUid, String mediaUid, MonocleLikeCardinality cardinality) {
        if (StringUtils.isEmpty(userUid) || StringUtils.isEmpty(mediaUid) || cardinality == null) {
            LOGGER.error("One or more of the required params was null or empty. userUid {}, mediaUid {}, cardinality {}", userUid, mediaUid, cardinality);
            return;
        }

        String name = this.monocleUidService.computeClass(mediaUid);
        if (name.equals(Article.class.getSimpleName())) {
            CACHE.removeArticle(userUid, mediaUid, cardinality);
        } else if (name.equals(Author.class.getSimpleName())) {
            CACHE.removeAuthor(userUid, mediaUid, cardinality);
        } else if (name.equals(Source.class.getSimpleName())) {
            CACHE.removeSource(userUid, mediaUid, cardinality);
        } else {
            LOGGER.error(MEDIA_INVALID);
        }
    }
}
