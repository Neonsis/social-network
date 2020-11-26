package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.CommunityResponse;
import org.neonsis.socialnetwork.service.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {

    private final CommunityService communityService;
    private final RestMapper restMapper;

    @PostMapping
    public ResponseEntity<CommunityResponse> createCommunity(@RequestBody CommunityCreateDto communityCreateDto) {
        CommunityDto communityDto = communityService.create(communityCreateDto);
        return ResponseEntity.ok(restMapper.communityDtoToResponse(communityDto));
    }
}
