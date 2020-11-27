package org.neonsis.socialnetwork.model.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.security.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * The user's last name.
     */
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

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
    private List<Post> posts = new ArrayList<>();

    /**
     * Communities which this user created.
     */
    @OneToMany(mappedBy = "moderator", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Community> createdCommunities = new ArrayList<>();

    /**
     * Communities to which this user joined.
     */
    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Community> communities = new ArrayList<>();

    /**
     * Foreign key (relation) to the user's roles.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    /**
     * Add the post to {@link #posts}.
     *
     * @param post the post to add.
     * @throws NullPointerException if {@param post} is null
     */
    public void addPost(Post post) {
        Objects.requireNonNull(post, "Post parameter is not initialized");
        if (this.posts == null) {
            this.posts = new ArrayList<>();
        }
        this.posts.add(post);
        post.setAuthor(this);
    }

    /**
     * Remove the post from {@link #posts}.
     *
     * @param post the post to remove.
     * @throws NullPointerException if {@param post} is null
     */
    public void removePost(Post post) {
        Objects.requireNonNull(post, "Post parameter is not initialized");
        if (this.posts == null) {
            return;
        }
        this.posts.remove(post);
        post.setAuthor(null);
    }

    /**
     * Add the created community to {@link #createdCommunities}.
     *
     * @param community the community to add.
     * @throws NullPointerException if {@param community} is null
     */
    public void addCreatedCommunity(Community community) {
        Objects.requireNonNull(community, "Community parameter is not initialized");
        if (this.createdCommunities == null) {
            this.createdCommunities = new ArrayList<>();
        }
        this.createdCommunities.add(community);
        community.setModerator(this);
    }

    /**
     * Remove the created community from {@link #createdCommunities}.
     *
     * @param community the community to remove.
     * @throws NullPointerException if {@param community} is null
     */
    public void removeCreatedCommunity(Community community) {
        Objects.requireNonNull(community, "Community parameter is not initialized");
        if (this.createdCommunities == null) {
            return;
        }
        this.createdCommunities.remove(community);
        community.setModerator(null);
    }

    /**
     * Add the community to {@link #communities}.
     *
     * @param community the community to add.
     * @throws NullPointerException if {@param community} is null
     */
    public void addCommunity(Community community) {
        Objects.requireNonNull(community, "Community parameter is not initialized");
        if (this.communities == null) {
            this.communities = new ArrayList<>();
        }
        this.communities.add(community);
        community.getFollowers().add(this);
    }

    /**
     * Remove the community to {@link #communities}.
     *
     * @param community the community to add.
     * @throws NullPointerException if {@param community} is null
     */
    public void removeCommunity(Community community) {
        Objects.requireNonNull(community, "Community parameter is not initialized");
        if (this.communities == null) {
            return;
        }
        this.communities.remove(community);
        community.getFollowers().remove(this);
    }

    /**
     * Get a new {@link UserBuilder}.
     *
     * @return a new {@link UserBuilder}.
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * A functional programming {@link UserBuilder} builder.
     *
     * @author neonsis
     */
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
         * @see User#setRoles(List)
         */
        public UserBuilder roles(List<Role> roles) {
            this.getEntity().setRoles(roles);
            return this;
        }
    }
}
