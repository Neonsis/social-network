package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.mapper.PostMapper;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.persistence.repository.PostRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.PostService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PostServiceImplTest {

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommunityRepository communityRepositoryTest;

    @Mock
    private AuthenticationFacade authenticationFacade;

    private PostService postService;

    @BeforeEach
    public void setUp() {
        this.postService = new PostServiceImpl(
                postRepository,
                userRepository,
                communityRepositoryTest,
                postMapper,
                authenticationFacade
        );
    }

    @Test
    public void testGetUserPostsSuccess() {
        User user = User.builder().id(1L).build();
        Post post = Post.builder().content("TEST").id(1L).build();
        PageImpl<Post> posts = new PageImpl<>(List.of(post));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findPostsByAuthorId(1L, null)).thenReturn(posts);
        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);

        postService.findUserPosts(1L, null);

        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findPostsByAuthorId(1L, null);
        verify(postRepository, times(1)).isAlreadyLiked(1L, 1L);
    }

    @Test
    public void testGetUserPostsFailure() {
        assertThrows(EntityNotFoundException.class, () -> postService.findUserPosts(1L, null));
    }

    @Test
    public void testCreateSuccess() {
        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setContent("TEST");

        when(authenticationFacade.getLoggedInUser()).thenReturn(User.builder().id(1L).build());

        postService.saveUserPost(postCreateDto);

        ArgumentCaptor<Post> postArgument = ArgumentCaptor.forClass(Post.class);
        verify(authenticationFacade, times(1)).getLoggedInUser();
        verify(postRepository, times(1)).save(postArgument.capture());

        Post actual = postArgument.getValue();
        assertEquals("TEST", actual.getContent());
    }

   /* @Test
    public void testDeleteSuccess() {
        Post post = Post.builder().id(1L).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(postRepository.findPostByIdAndAuthorId(1L, 1L)).thenReturn(Optional.of(post));

        postService.deleteById(1L);

        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(postRepository, times(1)).findPostByIdAndAuthorId(1L, 1L);
        verify(postRepository, times(1)).delete(post);
    }*/

    @Test
    public void testDeleteFailure() {
        assertThrows(EntityNotFoundException.class, () -> postService.deleteById(1L));
    }


    @Test
    public void testLikePostSuccess() {
        User user = User.builder().email("test@gmail.com").id(1L).build();
        Post post = spy(new Post());

        when(authenticationFacade.getLoggedInUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.isAlreadyLiked(1L, 1L)).thenReturn(false);

        postService.like(1L);

        ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
        verify(post, times(1)).addLike(userArgument.capture());
        verify(postRepository, times(1)).save(post);
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).isAlreadyLiked(1L, 1L);

        User value = userArgument.getValue();

        assertEquals(user.getEmail(), value.getEmail());
    }

    @Test
    public void testLikePostFailurePostNotFound() {
        assertThrows(EntityNotFoundException.class, () -> postService.like(1L));
    }

    @Test
    public void testLikePostFailureAlreadyLiked() {
        User user = User.builder().id(1L).build();

        when(authenticationFacade.getLoggedInUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(postRepository.isAlreadyLiked(1L, 1L)).thenReturn(true);

        InvalidWorkFlowException invalidWorkFlowException
                = assertThrows(InvalidWorkFlowException.class, () -> postService.like(1L));

        String actual = invalidWorkFlowException.getMessage();

        assertEquals("You have already liked post with id: 1", actual);
    }

    @Test
    public void testUnlikePostSuccess() {
        User user = User.builder().email("test@gmail.com").id(1L).build();
        Post post = spy(new Post());

        when(authenticationFacade.getLoggedInUser()).thenReturn(user);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.unlike(1L);

        ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
        verify(post, times(1)).removeLike(userArgument.capture());
        verify(postRepository, times(1)).save(post);
        verify(postRepository, times(1)).findById(1L);

        User value = userArgument.getValue();

        assertEquals(user.getEmail(), value.getEmail());
    }

    @Test
    public void testUnlikePostFailure() {
        assertThrows(EntityNotFoundException.class, () -> postService.unlike(1L));
    }
}