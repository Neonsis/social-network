package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByIdAndModeratorId(Long communityId, Long userId);

    @Query("SELECT CASE WHEN (COUNT(c) > 0) THEN true ELSE false END " +
            "FROM Community c WHERE c.id = :communityId AND :user MEMBER OF c.followers")
    boolean isUserAlreadyJoined(Long communityId, User user);
}
