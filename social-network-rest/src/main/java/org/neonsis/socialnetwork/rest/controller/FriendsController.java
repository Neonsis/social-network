package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.rest.payload.mapper.UserRestMapper;
import org.neonsis.socialnetwork.rest.payload.response.UserResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.rest.security.UserPrincipal;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_NUMBER;
import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@Controller
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendshipService friendshipService;
    private final UserRestMapper userRestMapper;

    @GetMapping("/{uuid}")
    public ResponseEntity<PageDto<UserResponse>> findFriends(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @PathVariable String uuid) {
        PageDto<UserDto> friendsDto = friendshipService.getFriends(uuid, pageable);
        PageDto<UserResponse> friendsResponse = userRestMapper.pageUserDtoToPageUserResponse(friendsDto);
        return new ResponseEntity<>(friendsResponse, HttpStatus.OK);
    }

    @GetMapping("/{uuid}/pending")
    public ResponseEntity<PageDto<UserResponse>> findUsersPendingFriendship(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @PathVariable String uuid) {
        PageDto<UserDto> pendingUsersDto = friendshipService.getPendingUsers(uuid, pageable);
        PageDto<UserResponse> pendingUsersResponse = userRestMapper.pageUserDtoToPageUserResponse(pendingUsersDto);
        return new ResponseEntity<>(pendingUsersResponse, HttpStatus.OK);
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<HttpStatus> addToFriend(@CurrentUser UserPrincipal userPrincipal, @PathVariable String uuid) {
        friendshipService.addToFriends(userPrincipal.getUuid(), uuid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{uuid}/accept")
    public ResponseEntity<HttpStatus> acceptFriendship(@CurrentUser UserPrincipal userPrincipal, @PathVariable String uuid) {
        friendshipService.acceptFriendship(userPrincipal.getUuid(), uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{uuid}/reject")
    public ResponseEntity<HttpStatus> rejectFriendship(@CurrentUser UserPrincipal userPrincipal, @PathVariable String uuid) {
        friendshipService.deleteFriendship(userPrincipal.getUuid(), uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
