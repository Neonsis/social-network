package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndProfileId(Long commendId, Long userId);
}
