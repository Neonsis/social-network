package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByAuthorId(Long authorId, Pageable pageable);

    Page<Post> findPostsByCommunityId(Long communityId, Pageable pageable);

    Optional<Post> findPostByIdAndAuthorId(Long postId, Long authorId);

    @Query("SELECT p FROM Post p WHERE p.author.id IN " +
            "(SELECT CASE WHEN f.invited.id = :userId THEN f.inviter.id ELSE f.invited.id END " +
            "FROM Friendship f WHERE (f.invited.id = :userId OR f.inviter.id = :userId) AND (f.status = 1))")
    Page<Post> findFriendsPosts(Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN (COUNT(p) > 0) THEN true ELSE false END FROM Post p " +
            "JOIN p.usersLikes ul WHERE ul.id = :userId AND p.id = :postId")
    boolean isAlreadyLiked(Long postId, Long userId);
}
