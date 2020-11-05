package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.BaseEntityAudit;
import org.neonsis.socialnetwork.model.domain.post.Post;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntityAudit implements Serializable {

    private static final long serialVersionUID = 4943199878136168288L;

    @Column(name = "uuid", nullable = false, unique = true, length = 120)
    private String uuid;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "encrypted_password", nullable = false)
    private String encryptedPassword;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts = new ArrayList<>();
}
