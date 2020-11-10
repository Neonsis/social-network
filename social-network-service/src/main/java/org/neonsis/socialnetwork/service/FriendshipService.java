package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

public interface FriendshipService {

    void addToFriends(Long loggedInUserId, Long friendId);

    PageDto<UserDto> getFriends(Long userId, Pageable pageable);

    PageDto<UserDto> getPendingUsers(Long userId, Pageable pageable);

    void acceptFriendship(Long loggedInUserId, Long friendId);

    void deleteFriendship(Long loggedInUserId, Long friendId);

    boolean isUserFriend(Long loggedInUserId, Long friendId);

    boolean isUserPendingFriendship(Long loggedInUserId, Long friendId);
}
