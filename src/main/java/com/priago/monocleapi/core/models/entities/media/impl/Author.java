package com.priago.monocleapi.core.models.entities.media.impl;

import com.google.common.collect.Lists;
import com.priago.monocleapi.core.models.entities.like.impl.AuthorLike;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Class representation of an author, an individual who writes a published {@link Article}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Entity
@NoArgsConstructor
public class Author implements MonocleMedia {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private Source source;

    @Getter
    @Setter
    @NonNull
    @Column(unique = true)
    private String code = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    private String name = StringUtils.EMPTY;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AuthorLike> authorLikes;


    //  METHODS

    @Override
    public List<String> getSortableAttributes() {
        return Lists.newArrayList("name", "pk", "source");
    }
}
