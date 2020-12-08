package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.ProfileDetailsResponse;
import org.neonsis.socialnetwork.rest.model.response.ProfileResponse;
import org.neonsis.socialnetwork.security.CurrentUser;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.ImageService;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final FriendshipService friendshipService;
    private final ProfileService profileService;
    private final ImageService imageService;

    private final RestMapper restMapper;

    @GetMapping("/me")
    public ProfileResponse getCurrentProfile(@CurrentUser User userPrincipal) {
        ProfileDto user = profileService.findByUserId(userPrincipal.getId());

        return restMapper.profileDtoToResponse(user);
    }

    @GetMapping("/{id}")
    public ProfileDetailsResponse getProfileById(@PathVariable Long id) {
        ProfileDto profile = profileService.findByUserId(id);
        ProfileDetailsResponse profileDetailsResponse = restMapper.profileDtoToDetailsResponse(profile);

        boolean isUserFriend = friendshipService.isUserFriend(id);
        if (isUserFriend) {
            profileDetailsResponse.setFriend(true);
        } else {
            boolean isFollower = friendshipService.isFollower(id);
            if (isFollower) {
                profileDetailsResponse.setFollower(isFollower);
            } else {
                boolean pendingFriendship = friendshipService.isPendingFriendship(id);
                profileDetailsResponse.setPendingFriendship(pendingFriendship);
            }
        }

        return profileDetailsResponse;
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<HttpStatus> uploadAvatar(@RequestParam("file") MultipartFile file) {
        boolean isUploaded = imageService.uploadUserAvatar(file);
        if (isUploaded) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ProfileResponse updateProfile(
            @Valid @RequestBody ProfileDto profileDto,
            @CurrentUser User userPrincipal
    ) {
        profileDto.setId(userPrincipal.getId());

        return toDto(profileService.update(profileDto));
    }

    private ProfileResponse toDto(ProfileDto profileDto) {
        return restMapper.profileDtoToResponse(profileDto);
    }
}
