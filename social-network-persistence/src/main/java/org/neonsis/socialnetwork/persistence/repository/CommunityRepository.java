package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByIdAndModeratorId(Long communityId, Long userId);

    @Query("SELECT c FROM Community c WHERE :user MEMBER OF c.followers")
    Page<Community> findUserCommunities(User user, Pageable pageable);

    @Query("SELECT c FROM Community c WHERE c.moderator.id = :userId")
    Page<Community> findModeratorCommunities(Long userId, Pageable pageable);

    Page<Community> findByTitleLike(String title, Pageable pageable);

    @Query("SELECT CASE WHEN (COUNT(c) > 0) THEN true ELSE false END " +
            "FROM Community c WHERE c.id = :communityId AND :user MEMBER OF c.followers")
    boolean isUserAlreadyJoined(Long communityId, User user);
}
