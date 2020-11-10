package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

public interface FriendshipService {

    void addToFriends(Long loggedInUserId, Long friendId);

    PageDto<UserDto> getFriends(Long userId, Pageable pageable);

    PageDto<UserDto> getFollowers(Long userId, Pageable pageable);

    void deleteFriendship(Long loggedInUserId, Long friendId);

    boolean isUserFriend(Long loggedInUserId, Long friendId);

    boolean isFollower(Long loggedInUserId, Long followerId);

    boolean isPendingFriendship(Long loggedInUserId, Long followerId);
}
