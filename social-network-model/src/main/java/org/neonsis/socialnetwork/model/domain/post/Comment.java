package org.neonsis.socialnetwork.model.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;

/**
 * Comment that user types to a post.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 5737119101681051526L;

    /**
     * The comment's content.
     */
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * The post to which this comment related.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private Post post;

    /**
     * The user who created this comment.
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private Profile profile;

    /**
     * Get a new {@link CommentBuilder}.
     *
     * @return a new {@link CommentBuilder}.
     */
    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    /**
     * A functional programming {@link CommentBuilder} builder.
     *
     * @author neonsis
     */
    public static class CommentBuilder extends AbstractBaseEntity.Builder<Comment> {

        @Override
        protected Comment buildEntity() {
            return new Comment();
        }

        /**
         * Set the comment content and return the builder.
         *
         * @param content the content of the comment being built.
         * @return the builder.
         * @see Comment#setContent(String)
         */
        public CommentBuilder content(String content) {
            this.getEntity().setContent(content);
            return this;
        }

        /**
         * Set the comment post and return the builder.
         *
         * @param post the post of the comment being built.
         * @return the builder.
         * @see Comment#setPost(Post)
         */
        public CommentBuilder post(Post post) {
            this.getEntity().setPost(post);
            return this;
        }

        /**
         * Set the comment user and return the builder.
         *
         * @param profile the user of the comment being built.
         * @return the builder.
         * @see Comment#setProfile(Profile)
         */
        public CommentBuilder profile(Profile profile) {
            this.getEntity().setProfile(profile);
            return this;
        }
    }
}
