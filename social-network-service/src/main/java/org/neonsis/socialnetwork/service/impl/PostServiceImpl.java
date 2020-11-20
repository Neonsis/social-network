package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.mapper.PostMapper;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.PostService;
import org.neonsis.socialnetwork.service.security.IAuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostMapper postMapper;

    private final IAuthenticationFacade authenticationFacade;

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
    public PostDto create(PostCreateDto postDto) {
        User user = authenticationFacade.getLoggedInUser();

        Post post = Post.builder()
                .content(postDto.getContent())
                .author(user)
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
        return postMapper.postToPostDto(post);
    }

    private Page<PostDto> toPageDto(Page<Post> postPage) {
        return postPage.map(postMapper::postToPostDto);
    }
}
