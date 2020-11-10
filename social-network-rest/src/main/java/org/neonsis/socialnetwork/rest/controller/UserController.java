package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.rest.payload.mapper.AuthRestMapper;
import org.neonsis.socialnetwork.rest.payload.mapper.UserRestMapper;
import org.neonsis.socialnetwork.rest.payload.response.UserDetailsResponse;
import org.neonsis.socialnetwork.rest.payload.response.UserResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.rest.security.UserPrincipal;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthRestMapper authRestMapper;
    private final UserRestMapper userRestMapper;
    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return new ResponseEntity<>(authRestMapper.userPrincipalToUserResponse(userPrincipal), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getProfileById(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        UserDto user = userService.findById(id);
        UserDetailsResponse userDetailsResponse = userRestMapper.userDtoToUserDetailsResponse(user);

        boolean isUserFriend = friendshipService.isUserFriend(userPrincipal.getId(), id);
        if (isUserFriend) {
            userDetailsResponse.setFriend(true);
        } else {
            boolean isUserPendingFriendship = friendshipService.isUserPendingFriendship(userPrincipal.getId(), id);
            userDetailsResponse.setPendingFriendship(isUserPendingFriendship);
        }

        return new ResponseEntity<>(userDetailsResponse, HttpStatus.OK);
    }
}
