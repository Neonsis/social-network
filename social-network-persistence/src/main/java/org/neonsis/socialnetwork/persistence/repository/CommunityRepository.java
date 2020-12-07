package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByIdAndModeratorId(Long communityId, Long userId);

    @Query("SELECT c FROM Community c WHERE :profile MEMBER OF c.followers AND LOWER(c.title) LIKE LOWER(CONCAT('%',:search,'%'))")
    Page<Community> findUserCommunities(Profile profile, String search, Pageable pageable);

    @Query("SELECT c FROM Community c WHERE c.moderator.id = :userId AND LOWER(c.title) LIKE LOWER(CONCAT('%',:search,'%'))")
    Page<Community> findModeratorCommunities(Long userId, String search, Pageable pageable);

    @Query("SELECT c FROM Community c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%',:search,'%'))")
    Page<Community> findCommunities(String search, Pageable pageable);

    @Query("SELECT p FROM Profile p WHERE :community MEMBER OF p.communities")
    Page<Profile> findCommunityFollowers(Community community, Pageable pageable);

    @Query("SELECT CASE WHEN (COUNT(c) > 0) THEN true ELSE false END " +
            "FROM Community c WHERE c.id = :communityId AND :user MEMBER OF c.followers")
    boolean isUserAlreadyJoined(Long communityId, Profile user);
}
