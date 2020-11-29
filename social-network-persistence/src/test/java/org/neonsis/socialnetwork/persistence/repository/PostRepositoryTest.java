package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link PostRepositoryTest} Integration Test.
 *
 * @author neonsis
 */
@DataJpaTest
@Sql("/test-data.sql")
class PostRepositoryTest {

    /**
     * The tested repository.
     */
    @Autowired
    private PostRepository postRepository;

    /**
     * Test method for {@link PostRepository#findPostsByAuthorId(Long, Pageable)}.
     */
    @Test
    public void testFindPostsByAuthorId() {
        Page<Post> postsByAuthorId = postRepository.findPostsByAuthorId(1L, null);

        List<Post> content = postsByAuthorId.getContent();

        assertEquals(content.size(), 1);
        assertEquals("TEST", content.get(0).getContent());
    }

    /**
     * Test method for {@link PostRepository#findPostsByAuthorId(Long, Pageable)}.
     */
    @Test
    public void testFindPostsByAuthorId_whenNotFound_returnEmptyList() {
        Page<Post> postsByAuthorId = postRepository.findPostsByAuthorId(2L, null);

        assertEquals(postsByAuthorId.getContent().size(), 0);
    }

    /**
     * Test method for {@link PostRepository#findPostByIdAndAuthorId(Long, Long)}.
     */
    @Test
    public void testFindPostsByIdAndAuthorId() {
        Optional<Post> postsByAuthorId = postRepository.findPostByIdAndAuthorId(1L, 1L);

        assertTrue(postsByAuthorId.isPresent());
    }

    /**
     * Test method for {@link PostRepository#findPostByIdAndAuthorId(Long, Long)}.
     */
    @Test
    public void testFindPostsByIdAndAuthorId_whenNotFound_returnEmptyOptional() {
        Optional<Post> postsByAuthorId = postRepository.findPostByIdAndAuthorId(2L, 1L);

        assertTrue(postsByAuthorId.isEmpty());
    }

    /**
     * Test method for {@link PostRepository#isAlreadyLiked(Long, Long)}.
     */
    @Test
    public void testIsAlreadyLiked_whenExists_returnTrue() {
        boolean alreadyLiked = postRepository.isAlreadyLiked(1L, 1L);

        assertTrue(alreadyLiked);
    }

    /**
     * Test method for {@link PostRepository#isAlreadyLiked(Long, Long)}.
     */
    @Test
    public void testIsAlreadyLiked_whenNotExists_returnFalse() {
        boolean alreadyLiked = postRepository.isAlreadyLiked(1L, 2L);

        assertFalse(alreadyLiked);
    }

    /**
     * Test method for {@link PostRepository#findFriendsAndCommunityPosts(Long, Pageable)}.
     */
    @Test
    public void testFindFriendsAndCommunityPosts() {
        Page<Post> posts = postRepository.findFriendsAndCommunityPosts(2L, null);

        assertEquals(2, posts.getSize());
    }

}