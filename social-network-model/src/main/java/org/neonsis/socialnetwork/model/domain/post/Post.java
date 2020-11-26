package org.neonsis.socialnetwork.model.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.util.*;

/**
 * Post.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 3044347078236487944L;

    /**
     * The post's content.
     */
    @Column(name = "content", nullable = false, length = 255)
    private String content;

    /**
     * The author of this post.
     */
    @ManyToOne(
            optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}
    )
    @JoinTable(
            name = "post_user",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private User author;

    @ManyToOne(
            optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}
    )
    @JoinTable(
            name = "post_community",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "community_id")}
    )
    private Community community;

    /**
     * The count of likes on this post.
     */
    @Formula("(select count(*) from post_like p where p.post_id = id)")
    private Integer countLike;

    /**
     * Users that liked this post.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> usersLikes = new HashSet<>();

    /**
     * Comments that users writes to this post.
     */
    @OrderBy("createdAt DESC")
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private final List<Comment> comments = new ArrayList<>();

    /**
     * Add the {@link #user} that likes this post to {@link usersLikes}.
     *
     * @param user the {@link #user} that likes this post.
     * @throws NullPointerException if {@link #user} is null.
     */
    public void addLike(User user) {
        Objects.requireNonNull(user, "User must not be null");
        this.usersLikes.add(user);
    }

    /**
     * Add the {@link #comment} to {@link comments}.
     *
     * @param comment the {@link #comment} to add.
     * @throws NullPointerException if {@link #comment} is null
     */
    public void addComment(Comment comment) {
        Objects.requireNonNull(comment, "Comment must not be null");
        this.comments.add(comment);
        comment.setPost(this);
    }

    /**
     * Delete the {@link #user} from {@link usersLikes}.
     *
     * @param user the {@link #user} to delete.
     */
    public void deleteLike(User user) {
        this.usersLikes.remove(user);
    }

    /**
     * Get a new {@link PostBuilder}.
     *
     * @return a new {@link PostBuilder}.
     */
    public static PostBuilder builder() {
        return new PostBuilder();
    }

    /**
     * A functional programming {@link PostBuilder} builder.
     *
     * @author neonsis
     */
    public static class PostBuilder extends AbstractBaseEntity.Builder<Post> {

        @Override
        protected Post buildEntity() {
            return new Post();
        }

        /**
         * Set the post content and return the builder.
         *
         * @param content the content of the post being built.
         * @return the builder.
         * @see Post#setContent(String)
         */
        public Post.PostBuilder content(String content) {
            this.getEntity().setContent(content);
            return this;
        }

        /**
         * Set the author of this post and return the builder.
         *
         * @param author the author of the post being built.
         * @return the builder.
         * @see Post#setAuthor(User)
         */
        public Post.PostBuilder author(User author) {
            this.getEntity().setAuthor(author);
            return this;
        }

        /**
         * Set the community of this post and return the builder.
         *
         * @param community the community of the post being built.
         * @return the builder.
         * @see Post#setCommunity(Community)
         */
        public Post.PostBuilder community(Community community) {
            this.getEntity().setCommunity(community);
            return this;
        }
    }
}


