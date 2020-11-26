package org.neonsis.socialnetwork.model.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.security.Role;

import javax.persistence.*;
import java.util.*;

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
public class User extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 4943199878136168288L;

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
     * The user's first name.
     */
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    /**
     * The user's lsat name.
     */
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(insertable = false)
    @Formula(value = "concat(first_name, ' ', last_name)")
    private String fullName;

    /**
     * The user's main avatar.
     */
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Image avatar;

    /**
     * The user's published posts.
     */
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Post> posts = new ArrayList<>();

    /**
     * Foreign key (relation) to the user's roles.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addPost(Post post) {
        Objects.requireNonNull(post, "Post must not be null");
        this.posts.add(post);
        post.setAuthor(this);
    }

    /**
     * Get a new {@link UserBuilder}.
     *
     * @return a new {@link UserBuilder}.
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder extends AbstractBaseEntity.Builder<User> {

        @Override
        protected User buildEntity() {
            return new User();
        }

        /**
         * Set the user email and return the builder.
         *
         * @param email the email of the user being built.
         * @return the builder.
         * @see User#setEmail(String)
         */
        public UserBuilder email(String email) {
            this.getEntity().setEmail(email);
            return this;
        }

        /**
         * Set the user password and return the builder.
         *
         * @param password the password of the user being built.
         * @return the builder.
         * @see User#setEncryptedPassword(String)
         */
        public UserBuilder password(String password) {
            this.getEntity().setEncryptedPassword(password);
            return this;
        }

        /**
         * Set the user first name and return the builder.
         *
         * @param firstName the first name of the user being built.
         * @return the builder.
         * @see User#setFirstName(String)
         */
        public UserBuilder firstName(String firstName) {
            this.getEntity().setFirstName(firstName);
            return this;
        }

        /**
         * Set the user last name and return the builder.
         *
         * @param lastName the last name of the user being built.
         * @return the builder.
         * @see User#setLastName(String)
         */
        public UserBuilder lastName(String lastName) {
            this.getEntity().setLastName(lastName);
            return this;
        }

        /**
         * Set the user avatar and return the builder.
         *
         * @param avatar the avatar of the user being built.
         * @return the builder.
         * @see User#setAvatar(Image)
         */
        public UserBuilder avatar(Image avatar) {
            this.getEntity().setAvatar(avatar);
            return this;
        }

        /**
         * Set the user roles and return the builder.
         *
         * @param roles the roles of the user being built.
         * @return the builder.
         * @see User#setRoles(Set)
         */
        public UserBuilder roles(Set<Role> roles) {
            this.getEntity().setRoles(roles);
            return this;
        }
    }
}
