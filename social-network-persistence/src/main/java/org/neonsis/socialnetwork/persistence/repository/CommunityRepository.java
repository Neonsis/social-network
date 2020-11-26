package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByIdAndModeratorId(Long communityId, Long userId);
}
