package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.FriendshipId;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.mapper.ProfileMapper;
import org.neonsis.socialnetwork.persistence.repository.FriendshipRepository;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.security.facade.AuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * {@link Friendship} service interface.
 *
 * @author neonsis
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final ProfileRepository profileRepository;

    private final AuthenticationFacade authenticationFacade;

    private final ProfileMapper profileMapper;

    @Override
    public void addToFriends(Long friendId) {
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();

        if (loggedInUserId.equals(friendId)) {
            throw new InvalidWorkFlowException("You can't add yourself as a friend");
        }

        Profile friend = profileRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + friendId));

        Optional<Friendship> friendshipOptional = friendshipRepository.findFriendship(loggedInUserId, friend.getId());
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
            friendship = Friendship.builder()
                    .id(
                            FriendshipId.builder()
                                    .inviterId(loggedInUserId)
                                    .invitedId(friend.getId())
                                    .build())
                    .status(Status.FOLLOWER)
                    .build();
        }

        friendshipRepository.save(friendship);
    }

    @Override
    public Page<ProfileDto> findUserFriends(Long userId, String search, Pageable pageable) {
        Page<Profile> friends = friendshipRepository.findFriends(userId, search, pageable);

        return friends.map(this::toDto);
    }

    @Override
    public Page<ProfileDto> findUserFollowers(Long userId, Pageable pageable) {
        Page<Profile> followers = friendshipRepository.findFollowers(userId, pageable);

        return followers.map(this::toDto);
    }

    @Override
    public void deleteById(Long friendId) {
        Friendship friendship = findFriendship(friendId)
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found by friend id: " + friendId));

        friendshipRepository.delete(friendship);
    }

    @Override
    public boolean isUserFriend(Long userId) {
        Optional<Friendship> friendship = findFriendship(userId);

        return friendship.isPresent() && friendship.get().getStatus().equals(Status.FRIEND);
    }

    @Override
    public boolean isFollower(Long userId) {
        Optional<Friendship> friendship = findFriendship(userId);
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();

        return friendship.isPresent()
                && friendship.get().getStatus().equals(Status.FOLLOWER)
                && friendship.get().getId().getInvitedId().equals(loggedInUserId);
    }

    @Override
    public boolean isPendingFriendship(Long userId) {
        Optional<Friendship> friendship = friendshipRepository.findFriendship(authenticationFacade.getLoggedInUserId(), userId);
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();

        return friendship.isPresent()
                && friendship.get().getStatus().equals(Status.FOLLOWER)
                && friendship.get().getId().getInviterId().equals(loggedInUserId);
    }

    private Optional<Friendship> findFriendship(Long userId) {
        return friendshipRepository.findFriendship(authenticationFacade.getLoggedInUserId(), userId);
    }

    private ProfileDto toDto(Profile profile) {
        return profileMapper.profileToDto(profile);
    }
}
