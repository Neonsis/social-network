package org.neonsis.socialnetwork.model.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.neonsis.socialnetwork.model.domain.base.BaseEntityAudit;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntityAudit implements Serializable {

    private static final long serialVersionUID = 3044347078236487944L;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(optional = false)
    private User author;

    @Formula("(select count(*) from post_like p where p.post_id = id)")
    private Integer countLike;

    @Transient
    private Boolean isLiked;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> usersLikes = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<Comment> comments = new HashSet<>();

    public void addLike(User user) {
        Objects.requireNonNull(user, "User must not be null");
        this.usersLikes.add(user);
    }

    public void deleteLike(User user) {
        this.usersLikes.remove(user);
    }
}
