package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.mapper.CommunityMapper;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.service.CommunityService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final AuthenticationFacade authenticationFacade;

    private final CommunityMapper communityMapper;

    @Override
    public CommunityDto create(CommunityCreateDto communityCreateDto) {
        User loggedInUser = authenticationFacade.getLoggedInUser();

        Community community = new Community();
        community.setTitle(communityCreateDto.getTitle());
        community.setModerator(loggedInUser);

        communityRepository.save(community);

        return toDto(community);
    }

    private CommunityDto toDto(Community community) {
        return communityMapper.communityToDto(community);
    }
}
