package com.priago.monocleapi.core.models.entities.like.impl;

import com.priago.monocleapi.core.enums.MonocleLikeCardinality;
import com.priago.monocleapi.core.models.entities.like.MonocleLike;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import com.priago.monocleapi.core.models.entities.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Implementation of {@link MonocleLike} for {@link Article}s
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class ArticleLike implements MonocleLike {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Getter
    @Setter
    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article entity;

    @Getter
    @Setter
    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    @NonNull
    @Column
    private String cardinality = MonocleLikeCardinality.NEUTRAL.getCode();

    @Getter
    @Setter
    @NonNull
    @Column
    private LocalDateTime dateTime;


    //  METHODS

    @Override
    public boolean isLike() {
        return this.cardinality.equals(MonocleLikeCardinality.LIKE.getCode());
    }

    @Override
    public boolean isDislike() {
        return this.cardinality.equals(MonocleLikeCardinality.DISLIKE.getCode());
    }
}
