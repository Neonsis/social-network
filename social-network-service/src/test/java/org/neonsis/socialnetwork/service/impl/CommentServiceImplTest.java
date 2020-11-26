package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.mapper.CommentMapper;
import org.neonsis.socialnetwork.model.dto.post.CommentCreateDto;
import org.neonsis.socialnetwork.persistence.repository.CommentRepository;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.service.CommentService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AuthenticationFacade authenticationFacade;

    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        this.commentService = new CommentServiceImpl(
                postRepository,
                commentRepository,
                commentMapper,
                authenticationFacade
        );
    }

    @Test
    public void testCreateSuccess() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setPostId(1L);
        commentCreateDto.setContent("TEST");
        Post post = spy(Post.builder().id(1L).build());
        User user = User.builder().id(1L).build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(authenticationFacade.getLoggedInUser()).thenReturn(user);

        commentService.create(commentCreateDto);

        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(postRepository, times(1)).findById(1L);
        verify(authenticationFacade, times(1)).getLoggedInUser();
        verify(post, times(1)).addComment(argument.capture());
        verify(commentRepository, times(1)).save(argument.getValue());
    }

    @Test
    public void testCreateFailure() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setPostId(1L);

        assertThrows(EntityNotFoundException.class, () -> commentService.create(commentCreateDto));
    }
}