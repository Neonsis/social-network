package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Friendship between two users.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "friendship")
@EntityListeners(AuditingEntityListener.class)
public class Friendship implements Serializable {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -3623764064798214158L;

    /**
     * The Unique Identifier (primary key) of this record.
     */
    @EmbeddedId
    private FriendshipId id;

    /**
     * The user who invite(create) friendship.
     */
    @MapsId("inviter_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE})
    private Profile inviter;

    /**
     * The user who is invited by another user.
     */
    @MapsId("invited_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE})
    private Profile invited;

    /**
     * An auto-populating date/time stamp of when the record was created.
     */
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private Date createdAt;

    /**
     * The status of this friendship.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Get a new {@link FriendshipBuilder}.
     *
     * @return a new {@link FriendshipBuilder}.
     */
    public static FriendshipBuilder builder() {
        return new FriendshipBuilder();
    }

    /**
     * A functional programming {@link FriendshipBuilder} builder.
     *
     * @author neonsis
     */
    public static class FriendshipBuilder {

        /**
         * The friendship currently being built.
         */
        private final Friendship friendship;

        /**
         * Create a {@link Friendship.FriendshipBuilder} with no initial property.
         */
        public FriendshipBuilder() {
            this.friendship = new Friendship();
        }

        /**
         * Build the friendship prepared by the builder.
         *
         * @return a new friendship as prepared by the builder.
         */
        public Friendship build() {
            return friendship;
        }

        /**
         * Set the id and return the builder.
         *
         * @param id the id of the friendship being built.
         * @return the builder.
         * @see Friendship#setId(FriendshipId)
         */
        public Friendship.FriendshipBuilder id(FriendshipId id) {
            friendship.setId(id);
            return this;
        }

        /**
         * Set the inviter and return the builder.
         *
         * @param inviter the inviter of the friendship being built.
         * @return the builder.
         * @see Friendship#setInviter(Profile)
         */
        public Friendship.FriendshipBuilder inviter(Profile inviter) {
            friendship.setInviter(inviter);
            return this;
        }

        /**
         * Set the invited and return the builder.
         *
         * @param invited the invited of the friendship being built.
         * @return the builder.
         * @see Friendship#setInvited(Profile)
         */
        public Friendship.FriendshipBuilder invited(Profile invited) {
            friendship.setInvited(invited);
            return this;
        }

        /**
         * Set the status and return the builder.
         *
         * @param status the status of the friendship being built.
         * @return the builder.
         * @see Friendship#setStatus(Status)
         */
        public Friendship.FriendshipBuilder status(Status status) {
            friendship.setStatus(status);
            return this;
        }
    }
}
