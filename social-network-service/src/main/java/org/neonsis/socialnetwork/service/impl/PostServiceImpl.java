package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.PostDto;
import org.neonsis.socialnetwork.model.dto.mapper.PostMapper;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PageDto<PostDto> getUserPosts(Long userId, Long loggedInUserId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));
        Page<Post> userPosts = postRepository.findPostsByAuthorId(userId, pageable);
        userPosts.getContent()
                .forEach(post -> post.setIsLiked(postRepository.isAlreadyLiked(post.getId(), loggedInUserId)));
        return postMapper.pagePostToPageDtoPostDto(userPosts);
    }

    @Override
    public PostDto create(Long userId, PostDto postDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));

        Post post = postMapper.postDtoToPost(postDto);
        post.setAuthor(user);
        Post saved = postRepository.save(post);
        return postMapper.postToPostDto(saved);
    }

    @Override
    public void delete(Long postId, Long authorId) {
        Post post = postRepository.findPostByIdAndAuthorId(postId, authorId)
                .orElseThrow(() -> new RecordNotFoundException("Post not found by id: " + postId));
        postRepository.delete(post);
    }

    @Override
    public void likePost(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RecordNotFoundException("Post not found by id: " + postId));
        boolean alreadyLiked = postRepository.isAlreadyLiked(postId, userId);
        if (alreadyLiked) {
            throw new InvalidWorkFlowException("You have already liked post with id: " + postId);
        }
        post.addLike(user);
        postRepository.save(post);
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RecordNotFoundException("Post not found by id: " + postId));
        post.deleteLike(user);
        postRepository.save(post);
    }
}
