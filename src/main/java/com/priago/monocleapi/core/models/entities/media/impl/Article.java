package com.priago.monocleapi.core.models.entities.media.impl;

import com.google.common.collect.Lists;
import com.priago.monocleapi.core.models.entities.like.impl.ArticleLike;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Class representation of a published article
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Article implements MonocleMedia {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Getter
    @Setter
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    @Getter
    @Setter
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Getter
    @Setter
    @NonNull
    @Column
    private String title = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column(columnDefinition = "text")
    private String description = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column(unique = true)
    private String url = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    private String urlToImage = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    private LocalDateTime publishedAt = LocalDateTime.now();

    @Getter
    @Setter
    @NonNull
    @Column(columnDefinition = "text")
    private String content = StringUtils.EMPTY;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArticleLike> articleLikes;


    //  METHODS

    @Override
    public List<String> getSortableAttributes() {
        return Lists.newArrayList("author", "pk", "publishedAt", "source", "title");
    }
}
