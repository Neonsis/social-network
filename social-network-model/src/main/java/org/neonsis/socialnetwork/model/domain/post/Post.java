package org.neonsis.socialnetwork.model.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "content",columnDefinition = "TEXT",nullable = false)
    private String content;

    /**
     * The author of this post.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "post_user",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private User author;

    /**
     * The community which made this post
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
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
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersLikes = new ArrayList<>();

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
    private List<Comment> comments = new ArrayList<>();

    /**
     * Add the user that likes this post to {@link #usersLikes}.
     *
     * @param user the user that likes this post.
     * @throws NullPointerException if {@param user} is null.
     */
    public void addLike(User user) {
        Objects.requireNonNull(user, "User parameter is not initialized");
        if (this.usersLikes == null) {
            this.usersLikes = new ArrayList<>();
        }
        this.usersLikes.add(user);
    }

    /**
     * Remove the user from {@link #usersLikes}.
     *
     * @param user the user which we want to remove.
     */
    public void removeLike(User user) {
        Objects.requireNonNull(user, "User parameter is not initialized");
        if (this.usersLikes == null) {
            return;
        }
        this.usersLikes.remove(user);
    }

    /**
     * Add the comment to {@link #comments}.
     *
     * @param comment the comment to add.
     * @throws NullPointerException if {@param comment} is null
     */
    public void addComment(Comment comment) {
        Objects.requireNonNull(comment, "Comment parameter is not initialized");
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
        comment.setPost(this);
    }

    /**
     * Remove the comment from {@link #comments}.
     *
     * @param comment the comment which we want to remove.
     */
    public void removeComment(Comment comment) {
        Objects.requireNonNull(comment, "Comment parameter is not initialized");
        if (this.comments == null) {
            return;
        }
        this.comments.remove(comment);
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


