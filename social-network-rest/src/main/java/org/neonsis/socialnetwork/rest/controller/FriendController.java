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

@Controller
@RequestMapping
@RequiredArgsConstructor
public class FriendController {

    private final FriendshipService friendshipService;
    private final RestMapper restMapper;

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<Page<UserResponse>> findUserFriends(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @PathVariable Long id) {
        Page<UserDto> friendsDto = friendshipService.getUserFriends(id, search, pageable);

        return ResponseEntity.ok(toPageResponse(friendsDto));
    }

    @GetMapping("/users/{id}/followers")
    public ResponseEntity<Page<UserResponse>> findUserFollowers(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @PathVariable Long id) {
        Page<UserDto> pendingUsersDto = friendshipService.getFollowers(id, pageable);
        return ResponseEntity.ok(toPageResponse(pendingUsersDto));
    }

    @PostMapping("/friends/{id}")
    public ResponseEntity<HttpStatus> addToFriend(@PathVariable Long id) {
        friendshipService.addToFriends(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<HttpStatus> deleteFriend(@PathVariable Long id) {
        friendshipService.delete(id);
        return ResponseEntity.ok().build();
    }

    private Page<UserResponse> toPageResponse(Page<UserDto> userDtoPage) {
        return userDtoPage.map(restMapper::userDtoToUserResponse);
    }
}
