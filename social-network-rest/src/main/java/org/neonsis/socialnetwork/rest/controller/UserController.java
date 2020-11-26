package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.UserDetailsResponse;
import org.neonsis.socialnetwork.rest.model.response.UserResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.UserService;
import org.neonsis.socialnetwork.service.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final RestMapper restMapper;
    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(restMapper.userPrincipalToResponse(userPrincipal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getProfileById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        UserDetailsResponse userDetailsResponse = restMapper.userDtoToDetailsResponse(user);

        boolean isUserFriend = friendshipService.isUserFriend(id);
        if (isUserFriend) {
            userDetailsResponse.setFriend(true);
        } else {
            boolean isFollower = friendshipService.isFollower(id);
            if (isFollower) {
                userDetailsResponse.setFollower(isFollower);
            } else {
                boolean pendingFriendship = friendshipService.isPendingFriendship(id);
                userDetailsResponse.setPendingFriendship(pendingFriendship);
            }
        }

        return ResponseEntity.ok(userDetailsResponse);
    }
}
