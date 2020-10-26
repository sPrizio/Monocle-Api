package com.priago.monocleapi.api.resources.entities.user;

import com.priago.monocleapi.api.resources.entities.MonocleResource;
import com.priago.monocleapi.api.resources.entities.media.impl.ArticleResource;
import com.priago.monocleapi.api.resources.entities.media.impl.AuthorResource;
import com.priago.monocleapi.api.resources.entities.media.impl.SourceResource;
import com.priago.monocleapi.core.models.entities.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * A DTO for {@link User}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class UserResource implements MonocleResource, Comparable<UserResource> {

    @Getter
    private String uid;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private Set<ArticleResource> likedArticles;

    @Getter
    @Setter
    private Set<ArticleResource> dislikedArticles;

    @Getter
    @Setter
    private Set<AuthorResource> likedAuthors;

    @Getter
    @Setter
    private Set<AuthorResource> dislikedAuthors;

    @Getter
    @Setter
    private Set<SourceResource> likedSources;

    @Getter
    @Setter
    private Set<SourceResource> dislikedSources;


    //  METHODS

    @Override
    public boolean isPresent() {
        return StringUtils.isNotEmpty(this.uid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResource that = (UserResource) o;
        return this.uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uid);
    }

    @Override
    public int compareTo(UserResource o) {
        return this.lastName.compareTo(o.getLastName());
    }
}
