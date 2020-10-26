package com.priago.monocleapi.api.resources.entities.media.impl;

import com.priago.monocleapi.api.resources.entities.media.MonocleMediaResource;
import com.priago.monocleapi.api.resources.entities.user.UserResource;
import com.priago.monocleapi.core.models.entities.media.impl.Article;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for {@link Article}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
public class ArticleResource implements MonocleMediaResource, Comparable<ArticleResource> {

    @Getter
    private String uid;

    @Getter
    @Setter
    private SourceResource source;

    @Getter
    @Setter
    private AuthorResource author;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String urlToImage;

    @Getter
    @Setter
    private LocalDateTime publishedAt;

    @Getter
    @Setter
    private String content;

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
        return StringUtils.isNotEmpty(this.uid) || StringUtils.isNotEmpty(this.url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleResource that = (ArticleResource) o;
        return this.uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uid);
    }

    @Override
    public int compareTo(ArticleResource o) {
        return this.publishedAt.compareTo(o.getPublishedAt());
    }
}
