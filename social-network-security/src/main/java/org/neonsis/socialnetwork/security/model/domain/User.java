package org.neonsis.socialnetwork.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    /**
     * An auto-populating date/time stamp of when the record was created.
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private Date createdAt;

    /**
     * An auto-populating date/time stamp of when the record was last updated.
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Date updatedAt;

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
