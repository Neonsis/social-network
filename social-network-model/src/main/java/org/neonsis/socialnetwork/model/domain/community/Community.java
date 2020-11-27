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
import java.util.Objects;

/**
 * Community dedicated to a topic or experience that is owned collectively by the community connected to it.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "community")
public class Community extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 2467230373145314620L;

    /**
     * The name of this community.
     */
    @Column(name = "title")
    private String title;

    /**
     * The creator of this community.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private User moderator;

    /**
     * The avatar of this community.
     */
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Image avatar;

    /**
     * The published posts of this community.
     */
    @OneToMany(mappedBy = "community", cascade = {CascadeType.MERGE})
    private List<Post> posts = new ArrayList<>();

    /**
     * Followers of this community.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "community_user",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> followers = new ArrayList<>();

    /**
     * Add the post to {@link #posts}.
     *
     * @param post the post which we want to save.
     * @throws NullPointerException if {@param post} is null.
     */
    public void addPost(Post post) {
        Objects.requireNonNull(post, "Post parameter is not initialized");
        if (this.posts == null) {
            this.posts = new ArrayList<>();
        }
        this.posts.add(post);
        post.setCommunity(this);
    }

    /**
     * Remove the post from {@link #posts}.
     *
     * @param post the post which we want to remove.
     * @throws NullPointerException if {@param post} is null.
     */
    public void removePost(Post post) {
        Objects.requireNonNull(post, "Post parameter is not initialized");
        if (this.posts == null) {
            return;
        }
        this.posts.remove(post);
        post.setCommunity(null);
    }

    /**
     * Add the follower to {@link #followers}.
     *
     * @param follower the follower which we want to save.
     * @throws NullPointerException if {@param follower} is null.
     */
    public void addFollower(User follower) {
        Objects.requireNonNull(follower, "User parameter is not initialized");
        if (this.followers == null) {
            this.followers = new ArrayList<>();
        }
        this.followers.add(follower);
        follower.getCommunities().add(this);
    }

    /**
     * Remove the follower from {@link #followers}.
     *
     * @param follower the follower which we want to remove.
     * @throws NullPointerException if {@param follower} is null.
     */
    public void removeFollower(User follower) {
        Objects.requireNonNull(follower, "User parameter is not initialized");
        if (this.followers == null) {
            this.followers = new ArrayList<>();
        }
        this.followers.remove(follower);
        follower.getCommunities().remove(this);
    }

    /**
     * Get a new {@link CommunityBuilder}.
     *
     * @return a new {@link CommunityBuilder}.
     */
    public static CommunityBuilder builder() {
        return new CommunityBuilder();
    }

    /**
     * A functional programming {@link CommunityBuilder} builder.
     *
     * @author neonsis
     */
    public static class CommunityBuilder extends AbstractBaseEntity.Builder<Community> {

        @Override
        protected Community buildEntity() {
            return new Community();
        }

        /**
         * Set the title and return the builder.
         *
         * @param title the title of the community being built.
         * @return the builder.
         * @see Community#setTitle(String)
         */
        public CommunityBuilder title(String title) {
            this.getEntity().setTitle(title);
            return this;
        }

        /**
         * Set the moderator and return the builder.
         *
         * @param moderator the moderator of the community being built.
         * @return the builder.
         * @see Community#setModerator(User)
         */
        public CommunityBuilder moderator(User moderator) {
            this.getEntity().setModerator(moderator);
            return this;
        }

        /**
         * Set the avatar and return the builder.
         *
         * @param avatar the avatar of the community being built.
         * @return the builder.
         * @see Community#setAvatar(Image)
         */
        public CommunityBuilder avatar(Image avatar) {
            this.getEntity().setAvatar(avatar);
            return this;
        }
    }
}
