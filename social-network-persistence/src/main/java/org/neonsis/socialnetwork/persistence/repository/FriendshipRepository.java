package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.FriendshipId;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {

    boolean existsByInviterIdAndInvitedId(Long inviterId, Long invitedId);

    Optional<Friendship> findFriendshipByInviterUuidAndInvitedUuid(String inviterUuid, String invitedUuid);

    @Query("SELECT f FROM Friendship f WHERE (f.inviter.uuid = :inviterUuid AND f.invited.uuid = :invitedUuid) " +
            "OR (f.invited.uuid = :inviterUuid AND f.inviter.uuid = :invitedUuid)")
    Optional<Friendship> findFriendship(String inviterUuid, String invitedUuid);

    @Query("SELECT u FROM User u WHERE u.id IN " +
            "(SELECT CASE WHEN f.invited.id = :id THEN f.inviter.id ELSE f.invited.id END FROM Friendship f " +
            "WHERE (f.invited.id = :id OR f.inviter.id = :id) AND (f.status = 1))")
    Page<User> findFriends(Long id, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id IN " +
            "(SELECT CASE WHEN f.invited.id = :id THEN f.inviter.id ELSE f.invited.id END FROM Friendship f " +
            "WHERE (f.invited.id = :id) AND (f.status = 0))")
    Page<User> findRequestedFriendshipUsers(Long id, Pageable pageable);
}
