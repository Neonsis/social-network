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

        Optional<Friendship> friendshipOptional = friendshipRepository.findFriendship(currentUser.getId(), friend.getId());
        Friendship friendship;
        if (friendshipOptional.isPresent()) {
            friendship = friendshipOptional.get();
            if (friendshipOptional.get().getStatus().equals(Status.FOLLOW) && friendship.getId().getInvitedId().equals(loggedInUserId)) {
                friendship.setStatus(Status.FRIEND);
            } else {
                throw new InvalidWorkFlowException(String.format("User with id '%s' already your friend", friendId));
            }
        } else {
            FriendshipId friendshipId = new FriendshipId(currentUser.getId(), friend.getId());
            friendship = new Friendship();
            friendship.setId(friendshipId);
            friendship.setStatus(Status.FOLLOW);
        }

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
    public PageDto<UserDto> getFollowers(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found by id: " + userId));
        Page<User> followers = friendshipRepository.findFollowers(user.getId(), pageable);
        return userMapper.pageUserToPageDtoUserDto(followers);
    }

    @Override
    public void delete(Long loggedInUserId, Long friendId) {
        Friendship friendship = friendshipRepository.findFriendship(friendId, loggedInUserId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Friendship not found by ids: %s,%s", loggedInUserId, friendId)));

        friendshipRepository.delete(friendship);
    }

    @Override
    public boolean isUserFriend(Long loggedInUserId, Long friendId) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserId, friendId);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.FRIEND);
    }

    @Override
    public boolean isFollower(Long loggedInUserId, Long followerId) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserId, followerId);

        return friendship.isPresent()
                && friendship.get().getStatus().equals(Status.FOLLOW)
                && friendship.get().getId().getInvitedId().equals(loggedInUserId);
    }

    @Override
    public boolean isPendingFriendship(Long loggedInUserId, Long followerId) {
        Optional<Friendship> friendship = friendshipRepository
                .findFriendship(loggedInUserId, followerId);

        return friendship.isPresent()
                && friendship.get().getStatus().equals(Status.FOLLOW)
                && friendship.get().getId().getInviterId().equals(loggedInUserId);
    }
}
