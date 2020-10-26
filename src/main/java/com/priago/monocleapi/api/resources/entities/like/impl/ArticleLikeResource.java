package com.priago.monocleapi.api.resources.entities.like.impl;

import com.priago.monocleapi.api.resources.entities.like.MonocleLikeResource;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * Implementation of {@link MonocleLikeResource} for {@link ArticleLikeResource}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class ArticleLikeResource implements MonocleLikeResource {

    @Getter
    private String uid;

    @Getter
    @Setter
    private ArticleResource resource;

    @Getter
    @Setter
    private UserResource user;

    @Getter
    @Setter
    private MonocleLikeCardinality cardinality;

    @Getter
    @Setter
    private LocalDateTime dateTime;


    //  METHODS

    @Override
    public boolean isLike() {
        return this.cardinality.equals(MonocleLikeCardinality.LIKE);
    }

    @Override
    public boolean isDislike() {
        return this.cardinality.equals(MonocleLikeCardinality.DISLIKE);
    }

    @Override
    public boolean isPresent() {
        return StringUtils.isNotEmpty(this.uid);
    }
}
