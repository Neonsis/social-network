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
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.post.CommentCreateDto;
import org.neonsis.socialnetwork.model.mapper.CommentMapper;
import org.neonsis.socialnetwork.persistence.repository.CommentRepository;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.security.facade.AuthenticationFacade;
import org.neonsis.socialnetwork.service.CommentService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private ProfileRepository profileRepository;

    @Mock
    private AuthenticationFacade authenticationFacade;

    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        this.commentService = new CommentServiceImpl(
                postRepository,
                commentRepository,
                profileRepository,
                commentMapper,
                authenticationFacade
        );
    }

    @Test
    public void testCreateSuccess() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setContent("TEST");
        Post post = spy(Post.builder().id(1L).build());
        Profile user = Profile.builder().id(1L).build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(profileRepository.findById(1L)).thenReturn(Optional.of(user));

        commentService.addCommentToPost(commentCreateDto, 1L);


        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(postRepository, times(1)).findById(1L);
        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(post, times(1)).addComment(argument.capture());
        verify(commentRepository, times(1)).save(argument.getValue());
    }

    @Test
    public void testCreateFailure() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();

        assertThrows(EntityNotFoundException.class, () -> commentService.addCommentToPost(commentCreateDto, 1L));
    }

    @Test
    public void deleteByIdSuccess() {
        Comment expected = Comment.builder().content("TEST").id(1L).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(commentRepository.findByIdAndProfileId(1L, 1L)).thenReturn(Optional.of(expected));

        commentService.deleteById(1L);

        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(commentRepository, times(1)).findByIdAndProfileId(1L, 1L);
        verify(commentRepository, times(1)).delete(argument.capture());

        Comment actual = argument.getValue();

        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void deleteByIdFailure() {
        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(commentRepository.findByIdAndProfileId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commentService.deleteById(1L));

        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(commentRepository, times(1)).findByIdAndProfileId(1L, 1L);
        verifyNoMoreInteractions(commentRepository);
    }

}
