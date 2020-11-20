package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendshipService {

    void addToFriends(Long friendId);

    Page<UserDto> getUserFriends(Long userId, Pageable pageable);

    Page<UserDto> getFollowers(Long userId, Pageable pageable);

    void delete(Long friendId);

    boolean isUserFriend(Long friendId);

    boolean isFollower(Long followerId);

    boolean isPendingFriendship(Long followerId);
}
