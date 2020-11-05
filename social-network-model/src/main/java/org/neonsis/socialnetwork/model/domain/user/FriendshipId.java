package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class FriendshipId implements Serializable {

    private static final long serialVersionUID = -6500400027286181854L;

    @Column(name = "inviter_id")
    private Long inviterId;

    @Column(name = "invited_id")
    private Long invitedId;

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
