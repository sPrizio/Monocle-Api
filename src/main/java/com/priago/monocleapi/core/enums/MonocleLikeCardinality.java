package com.priago.monocleapi.core.enums;

import com.priago.monocleapi.core.models.entities.like.MonocleLike;
import lombok.Getter;

/**
 * An enum representing the cardinality of a {@link MonocleLike}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public enum MonocleLikeCardinality {

    DISLIKE("dislike", "Dislike"),
    LIKE("like", "Like"),
    NEUTRAL("neutral", "Neutral");

    @Getter
    private final String code;

    @Getter
    private final String name;

    MonocleLikeCardinality(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
