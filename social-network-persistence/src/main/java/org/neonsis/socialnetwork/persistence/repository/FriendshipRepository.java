package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.FriendshipId;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {

    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.inviter.id = :firstUserId AND f.invited.id = :secondUserId) " +
            "OR (f.invited.id = :firstUserId AND f.inviter.id = :secondUserId)")
    Optional<Friendship> findFriendship(Long firstUserId, Long secondUserId);


    @Query("SELECT p FROM Profile p WHERE p.id IN " +
            "(SELECT CASE WHEN f.invited.id = :id THEN f.inviter.id ELSE f.invited.id END FROM Friendship f " +
            "WHERE (f.invited.id = :id OR f.inviter.id = :id) AND (f.status = 1)) " +
            "AND LOWER(p.fullName) LIKE LOWER(CONCAT('%',:search,'%'))")
    Page<Profile> findFriends(Long id, String search, Pageable pageable);

    @Query("SELECT p FROM Profile p WHERE p.id IN " +
            "(SELECT CASE WHEN f.invited.id = :id THEN f.inviter.id ELSE f.invited.id END FROM Friendship f " +
            "WHERE (f.invited.id = :id) AND (f.status = 0))")
    Page<Profile> findFollowers(Long id, Pageable pageable);
}
