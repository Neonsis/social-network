package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.security.model.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Profile - additional information about {@link User}.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "profile")
public class Profile extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 4644116734879167994L;

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
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image avatar;

    /**
     * The user's first name and last name
     */
    @Column(insertable = false)
    @Formula(value = "concat(first_name, ' ', last_name)")
    private String fullName;

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
    @OneToMany(mappedBy = "moderator", orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Community> createdCommunities = new ArrayList<>();

    /**
     * Communities to which this user joined.
     */
    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Community> communities = new ArrayList<>();

    /**
     * The user's gender.
     */
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    /**
     * The user's birthday.
     */
    @Column(name = "birthday")
    private LocalDate birthday;

    /**
     * The additional information about user.
     */
    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    /**
     * The country where the user is from
     */
    @Column(name = "country")
    private String country;

    /**
     * The city where the user is from
     */
    @Column(name = "city")
    private String city;

    /**
     * The user who owns this profile
     */
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

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
     * Get a new {@link ProfileBuilder}.
     *
     * @return a new {@link ProfileBuilder}.
     */
    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }

    /**
     * A functional programming {@link ProfileBuilder} builder.
     *
     * @author neonsis
     */
    public static class ProfileBuilder extends AbstractBaseEntity.Builder<Profile> {

        @Override
        protected Profile buildEntity() {
            return new Profile();
        }

        /**
         * Set the user first name and return the builder.
         *
         * @param firstName the first name of the user being built.
         * @return the builder.
         * @see Profile#setFirstName(String)
         */
        public Profile.ProfileBuilder firstName(String firstName) {
            this.getEntity().setFirstName(firstName);
            return this;
        }

        /**
         * Set the user last name and return the builder.
         *
         * @param lastName the last name of the user being built.
         * @return the builder.
         * @see Profile#setLastName(String)
         */
        public Profile.ProfileBuilder lastName(String lastName) {
            this.getEntity().setLastName(lastName);
            return this;
        }

        /**
         * Set the user avatar and return the builder.
         *
         * @param avatar the avatar of the user being built.
         * @return the builder.
         * @see Profile#setAvatar(Image)
         */
        public Profile.ProfileBuilder avatar(Image avatar) {
            this.getEntity().setAvatar(avatar);
            return this;
        }

        /**
         * Set the user gender and return the builder.
         *
         * @param gender the gender of the user being built.
         * @return the builder.
         * @see Profile#setGender(Gender)
         */
        public ProfileBuilder gender(Gender gender) {
            this.getEntity().setGender(gender);
            return this;
        }

        /**
         * Set the user birthday and return the builder.
         *
         * @param birthday the birthday of the user being built.
         * @return the builder.
         * @see Profile#setBirthday(LocalDate)
         */
        public ProfileBuilder birthday(LocalDate birthday) {
            this.getEntity().setBirthday(birthday);
            return this;
        }

        /**
         * Set the user about and return the builder.
         *
         * @param about the about of the user being built.
         * @return the builder.
         * @see Profile#setAbout(String)
         */
        public ProfileBuilder about(String about) {
            this.getEntity().setAbout(about);
            return this;
        }

        /**
         * Set the user country and return the builder.
         *
         * @param country the country of the user being built.
         * @return the builder.
         * @see Profile#setCountry(String)
         */
        public ProfileBuilder country(String country) {
            this.getEntity().setCountry(country);
            return this;
        }

        /**
         * Set the user city and return the builder.
         *
         * @param city the city of the user being built.
         * @return the builder.
         * @see Profile#setCity(String)
         */
        public ProfileBuilder city(String city) {
            this.getEntity().setCity(city);
            return this;
        }

        /**
         * Set the user and return the builder.
         *
         * @param user the user of the profile being built.
         * @return the builder.
         * @see Profile#setUser(User)
         */
        public ProfileBuilder user(User user) {
            this.getEntity().setUser(user);
            return this;
        }
    }
}
