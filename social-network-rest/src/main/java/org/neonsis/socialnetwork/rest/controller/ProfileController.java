package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.rest.payload.mapper.ProfileRestMapper;
import org.neonsis.socialnetwork.rest.payload.response.ProfileResponse;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRestMapper profileRestMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long id) {
        ProfileDto profile = profileService.findByUserId(id);
        return new ResponseEntity<>(profileRestMapper.profileDtoToProfileResponse(profile), HttpStatus.OK);
    }
}
