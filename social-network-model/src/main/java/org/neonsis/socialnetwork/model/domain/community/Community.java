package org.neonsis.socialnetwork.model.domain.community;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.Image;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "community")
public class Community extends AbstractBaseEntity {

    @Column(name = "title")
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}
    )
    private User moderator;

    @OneToOne(orphanRemoval = false, cascade = CascadeType.ALL)
    private Image avatar;

    @OneToMany(mappedBy = "community")
    private final List<Post> posts = new ArrayList<>();
}
