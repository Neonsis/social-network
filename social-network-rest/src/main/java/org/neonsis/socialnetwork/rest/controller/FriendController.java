package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.UserResponse;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FriendController {

    private final FriendshipService friendshipService;

    private final RestMapper restMapper;

    @GetMapping("/users/{userId}/friends")
    public Page<UserResponse> findUserFriends(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @PathVariable Long userId
    ) {
        Page<UserDto> friendsDto = friendshipService.findUserFriends(userId, search, pageable);

        return toPageResponse(friendsDto);
    }

    @GetMapping("/users/{userId}/followers")
    public Page<UserResponse> findUserFollowers(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @PathVariable Long userId
    ) {
        Page<UserDto> pendingUsersDto = friendshipService.findUserFollowers(userId, pageable);

        return toPageResponse(pendingUsersDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/friends/{userId}")
    public void addToFriend(@PathVariable Long userId) {
        friendshipService.addToFriends(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/friends/{userId}")
    public void deleteFriend(@PathVariable Long userId) {
        friendshipService.deleteById(userId);
    }

    private Page<UserResponse> toPageResponse(Page<UserDto> userDtoPage) {
        return userDtoPage.map(restMapper::userDtoToResponse);
    }
}
