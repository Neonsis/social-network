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
    public void addToFriends(String loggedInUserUuid, String friendUuid) {
        if (loggedInUserUuid.equals(friendUuid)) {
            throw new InvalidWorkFlowException("You can't add yourself as a friend");
        }
        User currentUser = userRepository.findByUuid(loggedInUserUuid)
                .orElseThrow(() -> new RecordNotFoundException("User not found by uuid: " + loggedInUserUuid));
        User friend = userRepository.findByUuid(friendUuid)
                .orElseThrow(() -> new RecordNotFoundException("User not found by uuid: " + loggedInUserUuid));

        boolean isAlreadyFriend = friendshipRepository.existsByInviterIdAndInvitedId(currentUser.getId(), friend.getId());
        if (isAlreadyFriend) {
            throw new InvalidWorkFlowException(String.format("User with uuid '%s' already your friend", friendUuid));
        }

        FriendshipId friendshipId = new FriendshipId(currentUser.getId(), friend.getId());
        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setStatus(Status.PENDING);
        friendshipRepository.save(friendship);
    }

    @Override
    public PageDto<UserDto> getFriends(String uuid, Pageable pageable) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new RecordNotFoundException("User not found by uuid: " + uuid));
        Page<User> friends = friendshipRepository.findFriends(user.getId(), pageable);
        return userMapper.pageUserToPageDtoUserDto(friends);
    }

    @Override
    public PageDto<UserDto> getPendingUsers(String uuid, Pageable pageable) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new RecordNotFoundException("User not found by uuid: " + uuid));
        Page<User> pendingUsers = friendshipRepository.findRequestedFriendshipUsers(user.getId(), pageable);
        return userMapper.pageUserToPageDtoUserDto(pendingUsers);
    }

    @Override
    public void acceptFriendship(String loggedInUserUuid, String friendUuid) {
        Friendship friendship = friendshipRepository.findFriendshipByInviterUuidAndInvitedUuid(friendUuid, loggedInUserUuid)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Friendship not found by uuids: %s,%s", loggedInUserUuid, friendUuid)));

        friendship.setStatus(Status.ACCEPTED);

        friendshipRepository.save(friendship);
    }

    @Override
    public void deleteFriendship(String loggedInUserUuid, String friendUuid) {
        Friendship friendship = friendshipRepository.findFriendshipByInviterUuidAndInvitedUuid(friendUuid, loggedInUserUuid)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Friendship not found by uuids: %s,%s", loggedInUserUuid, friendUuid)));

        friendshipRepository.delete(friendship);
    }

    @Override
    public boolean isUserFriend(String loggedInUserUuid, String friendUuid) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserUuid, friendUuid);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.ACCEPTED);
    }

    @Override
    public boolean isUserPendingFriendship(String loggedInUserUuid, String friendUuid) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserUuid, friendUuid);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.PENDING);
    }
}
