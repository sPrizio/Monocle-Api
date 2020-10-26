package com.priago.monocleapi.api.resources.entities.media.impl;

import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.enums.MonocleCategory;
import com.priago.monocleapi.core.enums.MonocleCountry;
import com.priago.monocleapi.core.enums.MonocleLanguage;
import com.priago.monocleapi.core.models.entities.media.impl.Source;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * A DTO for {@link Source}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class SourceResource implements MonocleMediaResource, Comparable<SourceResource> {

    @Getter
    private String uid;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private MonocleCategory category;

    @Getter
    @Setter
    private MonocleLanguage language;

    @Getter
    @Setter
    private MonocleCountry country;

    @Getter
    @Setter
    private int likes;

    @Getter
    @Setter
    private int dislikes;

    @Getter
    @Setter
    private Set<UserResource> likedBy;

    @Getter
    @Setter
    private Set<UserResource> dislikedBy;


    //  METHODS

    @Override
    public boolean isPresent() {
        return StringUtils.isNotEmpty(this.uid) || (StringUtils.isNotEmpty(this.id) && StringUtils.isNotEmpty(this.name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceResource that = (SourceResource) o;
        return this.uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uid);
    }

    @Override
    public int compareTo(SourceResource o) {
        return this.name.compareTo(o.getName());
    }
}
