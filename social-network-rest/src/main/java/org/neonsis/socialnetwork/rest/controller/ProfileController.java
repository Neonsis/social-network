package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.ProfileResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.service.ProfileService;
import org.neonsis.socialnetwork.service.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final RestMapper restMapper;

    @GetMapping("/{id}")
    public ProfileResponse getProfile(@PathVariable Long id) {
        ProfileDto profile = profileService.findByUserId(id);

        return toDto(profile);
    }

    @PutMapping
    public ProfileResponse updateProfile(
            @Valid @RequestBody ProfileDto profileDto,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        profileDto.setId(userPrincipal.getId());

        return toDto(profileService.update(profileDto));
    }

    private ProfileResponse toDto(ProfileDto profileDto) {
        return restMapper.profileDtoToResponse(profileDto);
    }
}
