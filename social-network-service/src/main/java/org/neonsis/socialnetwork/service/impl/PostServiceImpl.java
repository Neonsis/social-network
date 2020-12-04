package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.AccessForbiddenException;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.model.mapper.PostMapper;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.PostService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link Post service interface.
 *
 * @author neonsis
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    private final PostMapper postMapper;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public Page<PostDto> findUserPosts(Long authorId, Pageable pageable) {
        userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + authorId));

        Page<Post> userPosts = postRepository.findPostsByAuthorId(authorId, pageable);

        return processPosts(userPosts);
    }

    @Override
    public Page<PostDto> findCommunityPosts(Long communityId, Pageable pageable) {
        communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community nof found by id: " + communityId));

        Page<Post> communityPosts = postRepository.findPostsByCommunityId(communityId, pageable);

        return processPosts(communityPosts);
    }

    @Override
    public Page<PostDto> findFeedPosts(Pageable pageable) {
        Page<Post> friendsPosts = postRepository.findFriendsAndCommunityPosts(authenticationFacade.getLoggedInUserId(), pageable);

        return processPosts(friendsPosts);
    }

    @Override
    public PostDto saveUserPost(PostCreateDto postDto) {
        User user = authenticationFacade.getLoggedInUser();

        Post post = Post.builder()
                .content(postDto.getContent())
                .author(user)
                .build();

        postRepository.save(post);

        return toDto(post);
    }

    @Override
    public PostDto saveCommunityPost(PostCreateDto postCreateDto, Long communityId) {
        Long userId = authenticationFacade.getLoggedInUserId();
        Community community = communityRepository.findByIdAndModeratorId(communityId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id: " + communityId));

        Post post = Post.builder()
                .community(community)
                .content(postCreateDto.getContent())
                .build();

        postRepository.save(post);

        return toDto(post);
    }

    @Override
    public void deleteById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found by id: " + postId));

        Long loggedInUserId = authenticationFacade.getLoggedInUserId();

        if (
                post.getAuthor() != null && !post.getAuthor().getId().equals(loggedInUserId) // author of the post is not loggedInUser
                ||
                post.getCommunity() != null && !post.getCommunity().getModerator().getId().equals(loggedInUserId) // moderator of the post is not loggedInUser
        ) {
            throw new AccessForbiddenException("");
        }

        postRepository.delete(post);
    }

    @Override
    public void like(Long postId) {
        User user = authenticationFacade.getLoggedInUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found by id: " + postId));

        boolean alreadyLiked = postRepository.isAlreadyLiked(postId, user.getId());
        if (alreadyLiked) {
            throw new InvalidWorkFlowException("You have already liked post with id: " + postId);
        }

        post.addLike(user);

        postRepository.save(post);
    }

    @Override
    public void unlike(Long postId) {
        User user = authenticationFacade.getLoggedInUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found by id: " + postId));

        post.removeLike(user);

        postRepository.save(post);
    }

    private Page<PostDto> processPosts(Page<Post> posts) {
        Page<PostDto> postsPage = posts.map(this::toDto);

        setAlreadyLikedField(postsPage);

        return postsPage;
    }

    private void setAlreadyLikedField(Page<PostDto> postDtos) {
        postDtos.getContent()
                .forEach(post -> post.setIsLiked(
                        postRepository.isAlreadyLiked(
                                post.getId(),
                                authenticationFacade.getLoggedInUserId())
                        )
                );
    }

    private PostDto toDto(Post post) {
        return postMapper.postToDto(post);
    }
}
