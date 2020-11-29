package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.mapper.CommentMapper;
import org.neonsis.socialnetwork.model.dto.post.CommentCreateDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;
import org.neonsis.socialnetwork.persistence.repository.CommentRepository;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.service.CommentService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.stereotype.Service;

/**
 * {@link Comment} service.
 *
 * @author neonsis
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public CommentDto addCommentToPost(CommentCreateDto commentDto, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found by id: " + postId));

        User user = authenticationFacade.getLoggedInUser();

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .user(user)
                .build();

        post.addComment(comment);

        commentRepository.save(comment);

        return toDto(comment);
    }

    @Override
    public void deleteById(Long id) {
        Long userId = authenticationFacade.getLoggedInUserId();
        Comment comment = commentRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found by id: " + id));

        commentRepository.delete(comment);
    }

    private CommentDto toDto(Comment comment) {
        return commentMapper.commentToDto(comment);
    }
}
