package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByAuthorId(Long authorId, Pageable pageable);

    Optional<Post> findPostByIdAndAuthorId(Long postId, Long authorId);
}
