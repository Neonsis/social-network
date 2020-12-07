package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.CommunityDetailsResponse;
import org.neonsis.socialnetwork.rest.model.response.CommunityResponse;
import org.neonsis.socialnetwork.rest.model.response.ProfileResponse;
import org.neonsis.socialnetwork.service.CommunityService;
import org.neonsis.socialnetwork.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final ImageService imageService;

    private final RestMapper restMapper;

    @GetMapping("/communities/{communityId}")
    public CommunityDetailsResponse getCommunity(@PathVariable Long communityId) {
        CommunityDetailsResponse community = restMapper.communityDtoToDetailsResponse(communityService.findById(communityId));

        boolean isFollow = communityService.isFollowerOfCommunity(communityId);
        community.setIsUserFollow(isFollow);

        return community;
    }

    @GetMapping("/users/{userId}/communities")
    public Page<CommunityResponse> getUserCommunities(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @PathVariable Long userId
    ) {
        Page<CommunityDto> communities = communityService.findUserFollowCommunities(userId, search, pageable);
        return communities.map(this::toResponse);
    }

    @GetMapping("/users/{moderatorId}/moderator/communities")
    public Page<CommunityResponse> getModeratorCommunities(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @PathVariable Long moderatorId
    ) {
        Page<CommunityDto> communities = communityService.findModeratorCommunities(moderatorId, search, pageable);
        return communities.map(this::toResponse);
    }

    @GetMapping("/communities/{communityId}/followers")
    public Page<ProfileResponse> getCommunityFollowers(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @PathVariable Long communityId
    ) {
        Page<ProfileDto> followers = communityService.findCommunityFollowers(communityId, pageable);
        return followers.map(restMapper::profileDtoToResponse);
    }

    @GetMapping("/communities")
    public Page<CommunityResponse> getCommunities(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search
    ) {
        Page<CommunityDto> communities = communityService.findCommunities(search, pageable);

        return communities.map(this::toResponse);
    }

    @PostMapping("/communities")
    public CommunityResponse createCommunity(@Valid @RequestBody CommunityCreateDto communityCreateDto) {
        CommunityDto communityDto = communityService.save(communityCreateDto);
        return restMapper.communityDtoToResponse(communityDto);
    }

    @PostMapping("/community/{communityId}/uploadAvatar")
    public ResponseEntity<HttpStatus> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long communityId
    ) {
        boolean isUploaded = imageService.uploadCommunityAvatar(file, communityId);
        if (isUploaded) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/communities/{communityId}/follow")
    public void follow(@PathVariable Long communityId) {
        communityService.join(communityId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/communities/{communityId}/unfollow")
    public void unfollow(@PathVariable Long communityId) {
        communityService.leave(communityId);
    }

    public CommunityResponse toResponse(CommunityDto communityDto) {
        return restMapper.communityDtoToResponse(communityDto);
    }
}
