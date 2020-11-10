package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.FriendshipId;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.model.dto.mapper.UserMapper;
import org.neonsis.socialnetwork.persistence.repository.FriendshipRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void addToFriends(Long loggedInUserId, Long friendId) {
        if (loggedInUserId.equals(friendId)) {
            throw new InvalidWorkFlowException("You can't add yourself as a friend");
        }
        User currentUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + loggedInUserId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + friendId));

        boolean isAlreadyFriend = friendshipRepository.existsByInviterIdAndInvitedId(currentUser.getId(), friend.getId());
        if (isAlreadyFriend) {
            throw new InvalidWorkFlowException(String.format("User with id '%s' already your friend", friendId));
        }

        FriendshipId friendshipId = new FriendshipId(currentUser.getId(), friend.getId());
        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setStatus(Status.PENDING);
        friendshipRepository.save(friendship);
    }

    @Override
    public PageDto<UserDto> getFriends(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));
        Page<User> friends = friendshipRepository.findFriends(user.getId(), pageable);
        return userMapper.pageUserToPageDtoUserDto(friends);
    }

    @Override
    public PageDto<UserDto> getPendingUsers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));
        Page<User> pendingUsers = friendshipRepository.findRequestedFriendshipUsers(user.getId(), pageable);
        return userMapper.pageUserToPageDtoUserDto(pendingUsers);
    }

    @Override
    public void acceptFriendship(Long loggedInUserId, Long friendId) {
        Friendship friendship = friendshipRepository.findFriendshipByInviterIdAndInvitedId(friendId, loggedInUserId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Friendship not found by ids: %s,%s", loggedInUserId, friendId)));

        friendship.setStatus(Status.ACCEPTED);

        friendshipRepository.save(friendship);
    }

    @Override
    public void deleteFriendship(Long loggedInUserId, Long friendId) {
        Friendship friendship = friendshipRepository.findFriendshipByInviterIdAndInvitedId(friendId, loggedInUserId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Friendship not found by ids: %s,%s", loggedInUserId, friendId)));

        friendshipRepository.delete(friendship);
    }

    @Override
    public boolean isUserFriend(Long loggedInUserId, Long friendId) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserId, friendId);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.ACCEPTED);
    }

    @Override
    public boolean isUserPendingFriendship(Long loggedInUserId, Long friendId) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserId, friendId);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.PENDING);
    }
}
