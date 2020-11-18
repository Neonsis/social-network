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
 * The Unique Identifier (primary key) of the friendship.
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
    @Column(name = "inviter_id")
    private Long inviterId;

    /**
     * The user id who is invited by another user.
     */
    @Column(name = "invited_id")
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
}
