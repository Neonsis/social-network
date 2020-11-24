package org.neonsis.socialnetwork.model.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Unique Identifier (primary key) of the {@link Friendship}.
 *
 * @author neonsis
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipId implements Serializable {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -6500400027286181854L;

    /**
     * The user id who invite(create) friendship.
     */
    @Column(name = "inviter_id", nullable = false)
    private Long inviterId;

    /**
     * The user id who is invited by another user.
     */
    @Column(name = "invited_id", nullable = false)
    private Long invitedId;

    @Override
    public String toString() {
        return "FriendshipId{" +
                "inviterId=" + inviterId +
                ", invitedId=" + invitedId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipId that = (FriendshipId) o;
        return Objects.equals(inviterId, that.inviterId) &&
                Objects.equals(invitedId, that.invitedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inviterId, invitedId);
    }

    /**
     * Get a new {@link FriendshipIdBuilder}.
     *
     * @return a new {@link FriendshipIdBuilder}.
     */
    public static FriendshipIdBuilder builder() {
        return new FriendshipIdBuilder();
    }

    /**
     * A functional programming {@link FriendshipId.FriendshipIdBuilder} builder.
     *
     * @author neonsis
     */
    public static class FriendshipIdBuilder {

        /**
         * The friendshipId currently being built.
         */
        private final FriendshipId friendshipId;

        /**
         * Create a {@link FriendshipId.FriendshipIdBuilder} with no initial property.
         */
        public FriendshipIdBuilder() {
            this.friendshipId = new FriendshipId();
        }

        /**
         * Build the friendship id prepared by the builder.
         *
         * @return a new friendship id as prepared by the builder.
         */
        public FriendshipId build() {
            return friendshipId;
        }

        /**
         * Set the inviter id and return the builder.
         *
         * @param id the inviter id of the friendship id being built.
         * @return the builder.
         * @see FriendshipId#setInviterId(Long)
         */
        public FriendshipId.FriendshipIdBuilder inviterId(Long id) {
            friendshipId.setInviterId(id);
            return this;
        }

        /**
         * Set the invited id and return the builder.
         *
         * @param id the invited id of the friendship id being built.
         * @return the builder.
         * @see FriendshipId#setInvitedId(Long)
         */
        public FriendshipId.FriendshipIdBuilder invitedId(Long id) {
            friendshipId.setInvitedId(id);
            return this;
        }
    }
}
