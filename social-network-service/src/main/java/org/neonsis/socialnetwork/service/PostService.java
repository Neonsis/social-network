package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * {@link Post service interface.
 *
 * @author neonsis
 */
public interface PostService {

    /**
     * Find published user posts.
     *
     * @param userId   the id of the user.
     * @param pageable paging conditions.
     *
     * @return a page of user's posts.
     *
     * @throws EntityNotFoundException if user not found.
     */
    Page<PostDto> findUserPosts(Long userId, Pageable pageable);

    /**
     * Find published community posts.
     *
     * @param communityId the id of the community.
     * @param pageable    paging conditions.
     *
     * @return a page of community's posts.
     *
     * @throws EntityNotFoundException if community not found.
     */
    Page<PostDto> findCommunityPosts(Long communityId, Pageable pageable);

    /**
     * Find friends and communities posts of the current logged in user.
     *
     * @param pageable paging conditions.
     *
     * @return a page of posts.
     */
    Page<PostDto> findFeedPosts(Pageable pageable);

    /**
     * Create a new user's post.
     *
     * @param postDto a post object describing the post to create.
     * @return the new post.
     */
    PostDto saveUserPost(PostCreateDto postDto);

    /**
     * Create a new community's post.
     *
     * @param postDto     a profile object describing the profile to updated.
     * @param communityId the id of the community.
     *
     * @return the new post.
     *
     * @throws EntityNotFoundException if community not found.
     */
    PostDto saveCommunityPost(PostCreateDto postDto, Long communityId);

    /**
     * Delete a post.
     *
     * @param postId the id of the post.
     *
     * @throws EntityNotFoundException if post not found.
     */
    void deleteById(Long postId);

    /**
     * Like post.
     *
     * @param postId the id of the post.
     *
     * @throws EntityNotFoundException if post not found.
     * @throws InvalidWorkFlowException if user already liked this post.
     */
    void like(Long postId);

    /**
     * Unlike post.
     *
     * @param postId the id of the post.
     *
     * @throws EntityNotFoundException if post not found.
     */
    void unlike(Long postId);
}
