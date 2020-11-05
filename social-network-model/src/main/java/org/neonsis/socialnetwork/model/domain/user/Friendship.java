package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "friendship")
public class Friendship implements Serializable {

    private static final long serialVersionUID = -3623764064798214158L;

    @EmbeddedId
    private FriendshipId id;

    @MapsId("inviter_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User inviter;

    @MapsId("invited_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User invited;

    @Column(name = "created_at")
    private Date createdAt;

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
}
