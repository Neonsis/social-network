package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

public interface FriendshipService {

    void addToFriends(String loggedInUserUuid, String friendUuid);

    PageDto<UserDto> getFriends(String uuid, Pageable pageable);

    PageDto<UserDto> getPendingUsers(String uuid, Pageable pageable);

    void acceptFriendship(String loggedInUserUuid, String friendUuid);

    void deleteFriendship(String loggedInUserUuid, String friendUuid);

    boolean isUserFriend(String loggedInUserUuid, String friendUuid);

    boolean isUserPendingFriendship(String loggedInUserUuid, String friendUuid);
}
