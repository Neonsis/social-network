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
import org.springframework.web.bind.annotation.*;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_NUMBER;
import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@Controller
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendshipService friendshipService;
    private final UserRestMapper userRestMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PageDto<UserResponse>> findFriends(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @PathVariable Long id) {
        PageDto<UserDto> friendsDto = friendshipService.getFriends(id, pageable);
        PageDto<UserResponse> friendsResponse = userRestMapper.pageUserDtoToPageUserResponse(friendsDto);
        return new ResponseEntity<>(friendsResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}/pending")
    public ResponseEntity<PageDto<UserResponse>> findUsersPendingFriendship(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @PathVariable Long id) {
        PageDto<UserDto> pendingUsersDto = friendshipService.getFollowers(id, pageable);
        PageDto<UserResponse> pendingUsersResponse = userRestMapper.pageUserDtoToPageUserResponse(pendingUsersDto);
        return new ResponseEntity<>(pendingUsersResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> addToFriend(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        friendshipService.addToFriends(userPrincipal.getId(), id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFriend(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        friendshipService.delete(userPrincipal.getId(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
