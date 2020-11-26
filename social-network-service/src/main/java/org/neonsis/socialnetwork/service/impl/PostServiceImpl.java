package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.mapper.PostMapper;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.PostService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    private final PostMapper postMapper;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public Page<PostDto> getUserPosts(Long authorId, Pageable pageable) {
        userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + authorId));

        Page<Post> userPosts = postRepository.findPostsByAuthorId(authorId, pageable);

        Page<PostDto> postDtoPageDto = toPageDto(userPosts);

        postDtoPageDto.getContent()
                .forEach(post -> post.setIsLiked(postRepository.isAlreadyLiked(post.getId(), authenticationFacade.getUserId())));

        return postDtoPageDto;
    }

    @Override
    public Page<PostDto> getCommunityPosts(Long communityId, Pageable pageable) {
        communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community nof found by id: " + communityId));

        Page<Post> posts = postRepository.findPostsByCommunityId(communityId, pageable);

        Page<PostDto> postDtos = toPageDto(posts);

        postDtos.getContent()
                .forEach(post -> post.setIsLiked(postRepository.isAlreadyLiked(post.getId(), authenticationFacade.getUserId())));

        return postDtos;
    }

    @Override
    public Page<PostDto> getFeedPosts(Pageable pageable) {
        Page<Post> friendsPosts = postRepository.findFriendsPosts(authenticationFacade.getUserId(), pageable);

        Page<PostDto> postDtos = toPageDto(friendsPosts);

        postDtos.getContent()
                .forEach(post -> post.setIsLiked(postRepository.isAlreadyLiked(post.getId(), authenticationFacade.getUserId())));

        return postDtos;
    }

    @Override
    public PostDto createUserPost(PostCreateDto postDto) {
        User user = authenticationFacade.getLoggedInUser();

        Post post = Post.builder()
                .content(postDto.getContent())
                .author(user)
                .build();

        postRepository.save(post);

        return toDto(post);
    }

    @Override
    public PostDto createCommunityPost(PostCreateDto postCreateDto, Long communityId) {
        Long userId = authenticationFacade.getUserId();
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
    public void delete(Long postId) {
        Post post = postRepository.findPostByIdAndAuthorId(postId, authenticationFacade.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found by id: " + postId));
        postRepository.delete(post);
    }

    @Override
    public void likePost(Long postId) {
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
    public void unlikePost(Long postId) {
        User user = authenticationFacade.getLoggedInUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found by id: " + postId));
        post.deleteLike(user);
        postRepository.save(post);
    }

    private PostDto toDto(Post post) {
        return postMapper.postToDto(post);
    }

    private Page<PostDto> toPageDto(Page<Post> postPage) {
        return postPage.map(postMapper::postToDto);
    }
}
