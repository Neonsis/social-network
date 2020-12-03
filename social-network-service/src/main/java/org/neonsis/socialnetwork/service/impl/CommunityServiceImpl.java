package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.mapper.CommunityMapper;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.CommunityService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private final CommunityMapper communityMapper;

    @Override
    public CommunityDto findById(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id : " + communityId));

        return toDto(community);
    }

    @Override
    public Page<CommunityDto> findUserFollowCommunities(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id : " + userId));

        Page<Community> userCommunities = communityRepository.findUserCommunities(user, pageable);

        return userCommunities.map(this::toDto);
    }

    @Override
    public Page<CommunityDto> findModeratorCommunities(Long moderatorId, Pageable pageable) {
        userRepository.findById(moderatorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id : " + moderatorId));

        Page<Community> userCommunities = communityRepository.findModeratorCommunities(moderatorId, pageable);

        return userCommunities.map(this::toDto);
    }

    @Override
    public Page<CommunityDto> findCommunities(String search, Pageable pageable) {
        Page<Community> communities = communityRepository.findByTitleLike(search, pageable);

        return communities.map(this::toDto);
    }

    @Override
    public CommunityDto save(CommunityCreateDto communityCreateDto) {
        User loggedInUser = authenticationFacade.getLoggedInUser();

        Community community = Community.builder()
                .moderator(loggedInUser)
                .title(communityCreateDto.getTitle())
                .build();

        communityRepository.save(community);

        community.setFollowersCount(0);

        return toDto(community);
    }

    @Override
    public boolean isFollowerOfCommunity(Long communityId) {
        User user = authenticationFacade.getLoggedInUser();

        return communityRepository.isUserAlreadyJoined(communityId, user);
    }

    @Override
    public void join(Long communityId) {
        User loggedInUser = authenticationFacade.getLoggedInUser();

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id : " + communityId));

        boolean isAlreadyJoined = communityRepository.isUserAlreadyJoined(communityId, loggedInUser);

        if (isAlreadyJoined) {
            throw new InvalidWorkFlowException("You're already joined to the community with id: " + communityId);
        }

        boolean isModerator = community.getModerator().getId().equals(loggedInUser.getId());

        if (isModerator) {
            throw new InvalidWorkFlowException("Moderator can't joined to the own group");
        }

        community.addFollower(loggedInUser);

        communityRepository.save(community);
    }

    @Override
    public void leave(Long communityId) {
        User loggedInUser = authenticationFacade.getLoggedInUser();

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id : " + communityId));

        community.removeFollower(loggedInUser);

        communityRepository.save(community);
    }

    private CommunityDto toDto(Community community) {
        return communityMapper.communityToDto(community);
    }
}
