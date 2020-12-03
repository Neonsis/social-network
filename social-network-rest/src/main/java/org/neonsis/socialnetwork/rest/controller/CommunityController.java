package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.CommunityResponse;
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

    @PostMapping("/communities")
    public CommunityResponse createCommunity(@Valid @RequestBody CommunityCreateDto communityCreateDto) {
        CommunityDto communityDto = communityService.save(communityCreateDto);
        return restMapper.communityDtoToResponse(communityDto);
    }

    @GetMapping("/users/{userId}/communities")
    public Page<CommunityResponse> getUserCommunities(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @PathVariable Long userId
    ) {
        Page<CommunityDto> communities = communityService.findUserFollowCommunities(userId, pageable);
        return communities.map(this::toResponse);
    }

    public CommunityResponse toResponse(CommunityDto communityDto) {
        return restMapper.communityDtoToResponse(communityDto);
    }
}
