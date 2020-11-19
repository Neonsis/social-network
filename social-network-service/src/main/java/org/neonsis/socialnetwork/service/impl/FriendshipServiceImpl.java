package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.FriendshipId;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.mapper.UserMapper;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.persistence.repository.FriendshipRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.security.IAuthenticationFacade;
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

    private final IAuthenticationFacade authenticationFacade;

    @Override
    public void addToFriends(Long friendId) {
        User currentUser = authenticationFacade.getLoggedInUser();
        Long loggedInUserId = currentUser.getId();

        if (loggedInUserId.equals(friendId)) {
            throw new InvalidWorkFlowException("You can't add yourself as a friend");
        }

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + friendId));

        Optional<Friendship> friendshipOptional = friendshipRepository.findFriendship(currentUser.getId(), friend.getId());
        Friendship friendship;

        if (friendshipOptional.isPresent()) {
            friendship = friendshipOptional.get();
            // Accept friendship
            if (friendshipOptional.get().getStatus().equals(Status.FOLLOWER) && friendship.getId().getInvitedId().equals(loggedInUserId)) {
                friendship.setStatus(Status.FRIEND);
            } else {
                throw new InvalidWorkFlowException(String.format("User with id '%s' is already your friend", friendId));
            }
        } else {
            FriendshipId friendshipId = new FriendshipId(currentUser.getId(), friend.getId());
            friendship = new Friendship();
            friendship.setId(friendshipId);
            friendship.setStatus(Status.FOLLOWER);
        }

        friendshipRepository.save(friendship);
    }

    @Override
    public PageDto<UserDto> getUserFriends(Long userId, Pageable pageable) {
        User user = authenticationFacade.getLoggedInUser();
        Page<User> friends = friendshipRepository.findFriends(user.getId(), pageable);

        return toPageDto(friends);
    }

    @Override
    public PageDto<UserDto> getFollowers(Long userId, Pageable pageable) {
        User user = authenticationFacade.getLoggedInUser();
        Page<User> followers = friendshipRepository.findFollowers(user.getId(), pageable);

        return toPageDto(followers);
    }

    @Override
    public void delete(Long friendId) {
        Friendship friendship = findFriendship(friendId)
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found by friend id: " + friendId));

        friendshipRepository.delete(friendship);
    }

    @Override
    public boolean isUserFriend(Long friendId) {
        Optional<Friendship> friendship = findFriendship(friendId);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.FRIEND);
    }

    @Override
    public boolean isFollower(Long followerId) {
        Optional<Friendship> friendship = findFriendship(followerId);
        Long loggedInUserId = authenticationFacade.getUserId();

        return friendship.isPresent()
                && friendship.get().getStatus().equals(Status.FOLLOWER)
                && friendship.get().getId().getInvitedId().equals(loggedInUserId);
    }

    @Override
    public boolean isPendingFriendship(Long userId) {
        Optional<Friendship> friendship = findFriendship(userId);
        Long loggedInUserId = authenticationFacade.getUserId();

        return friendship.isPresent()
                && friendship.get().getStatus().equals(Status.FOLLOWER)
                && friendship.get().getId().getInviterId().equals(loggedInUserId);
    }

    private Optional<Friendship> findFriendship(Long userId) {
        return friendshipRepository.findFriendship(authenticationFacade.getUserId(), userId);
    }

    private PageDto<UserDto> toPageDto(Page<User> user) {
        return userMapper.pageUserToPageDtoUserDto(user);
    }
}
