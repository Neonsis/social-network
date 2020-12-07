package org.neonsis.socialnetwork.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 4943199878136168288L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true, insertable = false)
    private Long id;

    /**
     * The user's account email.
     */
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    /**
     * The user's account hashed password.
     */
    @Column(name = "encrypted_password", nullable = false)
    private String encryptedPassword;

    /**
     * Foreign key (relation) to the user's roles.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
}
