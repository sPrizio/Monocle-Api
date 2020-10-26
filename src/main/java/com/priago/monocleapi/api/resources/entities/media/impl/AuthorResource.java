package com.priago.monocleapi.api.resources.entities.media.impl;

import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.models.entities.media.impl.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * A DTO for {@link Author}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class AuthorResource implements MonocleMediaResource, Comparable<AuthorResource> {

    @Getter
    private String uid;

    @Getter
    @Setter
    private SourceResource source;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String name;

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
        return StringUtils.isNotEmpty(this.uid) || (StringUtils.isNotEmpty(this.code) && StringUtils.isNotEmpty(this.name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorResource that = (AuthorResource) o;
        return this.uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uid);
    }

    @Override
    public int compareTo(AuthorResource o) {
        return this.name.compareTo(o.getName());
    }
}
