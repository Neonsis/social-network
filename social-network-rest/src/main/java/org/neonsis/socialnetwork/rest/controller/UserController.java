package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.UserDetailsResponse;
import org.neonsis.socialnetwork.rest.model.response.UserResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.ImageService;
import org.neonsis.socialnetwork.service.UserService;
import org.neonsis.socialnetwork.service.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final RestMapper restMapper;

    private final UserService userService;
    private final FriendshipService friendshipService;
    private final ImageService imageService;

    @GetMapping("/me")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserDto user = userService.findById(userPrincipal.getId());
        return restMapper.userDtoToResponse(user);
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<HttpStatus> uploadAvatar(@RequestParam("file") MultipartFile file) {
        boolean isUploaded = imageService.uploadUserAvatar(file);
        if(isUploaded){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public UserDetailsResponse getProfileById(@PathVariable Long id) {
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

        return userDetailsResponse;
    }
}
