package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.CommunityDetailsResponse;
import org.neonsis.socialnetwork.rest.model.response.CommunityResponse;
import org.neonsis.socialnetwork.rest.model.response.UserResponse;
import org.neonsis.socialnetwork.service.CommunityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_NUMBER;
import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final RestMapper restMapper;

    @GetMapping("/communities/{id}")
    public CommunityDetailsResponse getCommunity(@PathVariable Long id) {
        CommunityDetailsResponse community = restMapper.communityDtoToDetailsResponse(communityService.findById(id));

        boolean isFollow = communityService.isFollowerOfCommunity(id);
        community.setIsUserFollow(isFollow);

        return community;
    }

    @GetMapping("/users/{userId}/communities")
    public Page<CommunityResponse> getUserCommunities(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @PathVariable Long userId
    ) {
        Page<CommunityDto> communities = communityService.findUserFollowCommunities(userId, search, pageable);
        return communities.map(this::toResponse);
    }

    @GetMapping("/users/{moderatorId}/moderator/communities")
    public Page<CommunityResponse> getModeratorCommunities(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @PathVariable Long moderatorId
    ) {
        Page<CommunityDto> communities = communityService.findModeratorCommunities(moderatorId, search, pageable);
        return communities.map(this::toResponse);
    }

    @GetMapping("/communities/{communityId}/followers")
    public Page<UserResponse> getCommunityFollowers(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @PathVariable Long communityId
    ) {
        Page<UserDto> followers = communityService.findCommunityFollowers(communityId, pageable);
        return followers.map(restMapper::userDtoToResponse);
    }

    @GetMapping("/communities")
    public Page<CommunityResponse> getCommunities(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable,
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

    @PostMapping("/communities/{id}/follow")
    public void follow(@PathVariable Long id) {
        communityService.join(id);
    }

    @PostMapping("/communities/{id}/unfollow")
    public void unfollow(@PathVariable Long id) {
        communityService.leave(id);
    }

    public CommunityResponse toResponse(CommunityDto communityDto) {
        return restMapper.communityDtoToResponse(communityDto);
    }
}
