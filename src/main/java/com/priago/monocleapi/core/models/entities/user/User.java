package com.priago.monocleapi.core.models.entities.user;

import com.priago.monocleapi.core.models.entities.MonocleEntity;
import com.priago.monocleapi.core.models.entities.like.impl.ArticleLike;
import com.priago.monocleapi.core.models.entities.like.impl.AuthorLike;
import com.priago.monocleapi.core.models.entities.like.impl.SourceLike;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Class representation of a Monocle user
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements MonocleEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Getter
    @Setter
    @NonNull
    @Column(unique = true)
    private String username;

    @Getter
    @Setter
    @NonNull
    @Column
    private String firstName;

    @Getter
    @Setter
    @NonNull
    @Column
    private String lastName;

    @Getter
    @Setter
    @NonNull
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    @NonNull
    @Column
    private String password;

    @Getter
    @Setter
    @NonNull
    @Column
    private boolean active;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArticleLike> articleLikes;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AuthorLike> authorLikes;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SourceLike> sourceLikes;
}
