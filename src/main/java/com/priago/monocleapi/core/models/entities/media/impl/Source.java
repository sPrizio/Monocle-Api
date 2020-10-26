package com.priago.monocleapi.core.models.entities.media.impl;

import com.google.common.collect.Lists;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.models.entities.like.impl.SourceLike;
import com.priago.monocleapi.core.models.entities.media.MonocleMedia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Class representation of an organization that publishes {@link Article}s
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Entity
@NoArgsConstructor
public class Source implements MonocleMedia {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Getter
    @Setter
    @NonNull
    @Column(unique = true)
    private String id = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    private String name = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    private String description = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    private String url = StringUtils.EMPTY;

    @Getter
    @Setter
    @NonNull
    @Column
    @ColumnDefault("'N/A'")
    private String category = MonocleCategory.NOT_APPLICABLE.getCode();

    @Getter
    @Setter
    @NonNull
    @Column
    @ColumnDefault("'N/A'")
    private String language = MonocleLanguage.NOT_APPLICABLE.getCode();

    @Getter
    @Setter
    @NonNull
    @Column
    @ColumnDefault("'N/A'")
    private String country = MonocleCountry.NOT_APPLICABLE.getCode();

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SourceLike> sourceLikes;


    //  METHODS

    @Override
    public List<String> getSortableAttributes() {
        return Lists.newArrayList("category", "country", "language", "name", "pk");
    }
}
