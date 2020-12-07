package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.FriendshipId;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.mapper.UserMapper;
import org.neonsis.socialnetwork.persistence.repository.FriendshipRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FriendshipServiceImplTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationFacade authenticationFacade;

    private FriendshipService friendshipService;

    @BeforeEach
    public void setUp() {
        this.friendshipService = new FriendshipServiceImpl(
                friendshipRepository,
                userRepository,
                authenticationFacade,
                userMapper
        );
    }

    @Test
    public void testAddToFriendsAccept() {
        User loggedInUser = User.builder().id(1L).build();
        User friend = User.builder().id(2L).build();
        Friendship friendship = Friendship.builder().status(Status.FOLLOWER).id(new FriendshipId(2L, 1L)).build();

        when(authenticationFacade.getLoggedInUser()).thenReturn(loggedInUser);
        when(userRepository.findById(2L)).thenReturn(Optional.of(friend));
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.of(friendship));

        friendshipService.addToFriends(2L);

        ArgumentCaptor<Friendship> argument = ArgumentCaptor.forClass(Friendship.class);
        verify(authenticationFacade, times(1)).getLoggedInUser();
        verify(userRepository, times(1)).findById(2L);
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);
        verify(friendshipRepository, times(1)).save(argument.capture());

        Friendship actual = argument.getValue();

        assertEquals(2L, actual.getId().getInviterId());
        assertEquals(1L, actual.getId().getInvitedId());
        assertEquals(Status.FRIEND, actual.getStatus());
    }

    @Test
    public void testAddToFriends_whenFriendIsAlreadyFriend_thenThrowException() {
        User loggedInUser = User.builder().id(1L).build();
        User friend = User.builder().id(2L).build();
        Friendship friendship = Friendship.builder().status(Status.FRIEND).id(new FriendshipId(2L, 1L)).build();

        when(authenticationFacade.getLoggedInUser()).thenReturn(loggedInUser);
        when(userRepository.findById(2L)).thenReturn(Optional.of(friend));
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.of(friendship));

        InvalidWorkFlowException invalidWorkFlowException
                = assertThrows(InvalidWorkFlowException.class, () -> friendshipService.addToFriends(2L));

        verify(authenticationFacade, times(1)).getLoggedInUser();
        verify(userRepository, times(1)).findById(2L);
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);

        String message = invalidWorkFlowException.getMessage();

        assertEquals("User with id '2' is already your friend", message);
    }

    @Test
    public void testAddToFriendsSuccess() {
        User loggedInUser = User.builder().id(1L).build();
        User friend = User.builder().id(2L).build();

        when(authenticationFacade.getLoggedInUser()).thenReturn(loggedInUser);
        when(userRepository.findById(2L)).thenReturn(Optional.of(friend));
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.empty());

        friendshipService.addToFriends(2L);

        ArgumentCaptor<Friendship> argument = ArgumentCaptor.forClass(Friendship.class);
        verify(authenticationFacade, times(1)).getLoggedInUser();
        verify(userRepository, times(1)).findById(2L);
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);
        verify(friendshipRepository, times(1)).save(argument.capture());

        Friendship actual = argument.getValue();

        assertEquals(1L, actual.getId().getInviterId());
        assertEquals(2L, actual.getId().getInvitedId());
        assertEquals(Status.FOLLOWER, actual.getStatus());
    }

    @Test
    public void testAddToFriends_whenFriendNotFound_thenThrowException() {
        User loggedInUser = User.builder().id(1L).build();

        when(authenticationFacade.getLoggedInUser()).thenReturn(loggedInUser);
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> friendshipService.addToFriends(2L));
    }

    @Test
    public void testAddToFriends_whenUserAddYourSelf_thenThrowException() {
        User loggedInUser = User.builder().id(1L).build();

        when(authenticationFacade.getLoggedInUser()).thenReturn(loggedInUser);


        InvalidWorkFlowException invalidWorkFlowException =
                assertThrows(InvalidWorkFlowException.class, () -> friendshipService.addToFriends(1L));

        String message = invalidWorkFlowException.getMessage();

        assertEquals("You can't add yourself as a friend", message);
    }

    @Test
    public void testGetFriendsSuccess() {
        when(friendshipRepository.findFriends(1L, "", null)).thenReturn(new PageImpl<>(Collections.emptyList()));

        friendshipService.findUserFriends(1L, "", null);

        verify(friendshipRepository, times(1)).findFriends(1L, "", null);
    }

    @Test
    public void testGetFollowersSuccess() {
        when(friendshipRepository.findFollowers(1L, null)).thenReturn(new PageImpl<>(Collections.emptyList()));

        friendshipService.findUserFollowers(1L, null);

        verify(friendshipRepository, times(1)).findFollowers(1L, null);
    }

    @Test
    public void testDeleteSuccess() {
        Friendship friendship = Friendship.builder().id(new FriendshipId(1L, 2L)).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.of(friendship));

        friendshipService.deleteById(2L);

        ArgumentCaptor<Friendship> argument = ArgumentCaptor.forClass(Friendship.class);
        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).delete(argument.capture());

        Friendship actual = argument.getValue();

        assertEquals(friendship.getId().getInvitedId(), actual.getId().getInvitedId());
        assertEquals(friendship.getId().getInviterId(), actual.getId().getInviterId());
    }

    @Test
    public void testDeleteFailure() {
        assertThrows(EntityNotFoundException.class, () -> friendshipService.deleteById(2L));
    }

    @Test
    public void testIsFriendTrue() {
        Friendship friendship = Friendship.builder().id(new FriendshipId(1L, 2L)).status(Status.FRIEND).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.of(friendship));

        boolean isFriend = friendshipService.isUserFriend(2L);

        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);

        assertTrue(isFriend);
    }

    @Test
    public void testIsFriendFalse() {
        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.empty());

        boolean isFriend = friendshipService.isUserFriend(2L);

        verify(authenticationFacade, times(1)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);

        assertFalse(isFriend);
    }


    @Test
    public void testIsFollowerSuccess() {
        Friendship friendship = Friendship.builder().id(new FriendshipId(1L, 2L)).status(Status.FOLLOWER).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(2L);
        when(friendshipRepository.findFriendship(2L, 1L)).thenReturn(Optional.of(friendship));

        boolean isFollower = friendshipService.isFollower(1L);

        verify(authenticationFacade, times(2)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).findFriendship(2L, 1L);

        assertTrue(isFollower);
    }

    @Test
    public void testIsFollowerFalse_whenNotFollowerButPending_returnFalse() {
        Friendship friendship = Friendship.builder().id(new FriendshipId(1L, 2L)).status(Status.FOLLOWER).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.of(friendship));

        boolean isFollower = friendshipService.isFollower(2L);

        verify(authenticationFacade, times(2)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);

        assertFalse(isFollower);
    }

    @Test
    public void testIsPendingFriendshipSuccess() {
        Friendship friendship = Friendship.builder().id(new FriendshipId(1L, 2L)).status(Status.FOLLOWER).build();

        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.of(friendship));

        boolean pendingFriendship = friendshipService.isPendingFriendship(2L);

        verify(authenticationFacade, times(2)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);

        assertTrue(pendingFriendship);
    }

    @Test
    public void testIsPendingFriendship_whenNotFriends_returnFalse() {
        when(authenticationFacade.getLoggedInUserId()).thenReturn(1L);
        when(friendshipRepository.findFriendship(1L, 2L)).thenReturn(Optional.empty());

        boolean pendingFriendship = friendshipService.isPendingFriendship(2L);

        verify(authenticationFacade, times(2)).getLoggedInUserId();
        verify(friendshipRepository, times(1)).findFriendship(1L, 2L);

        assertFalse(pendingFriendship);
    }
}