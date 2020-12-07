package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.mapper.CommunityMapper;
import org.neonsis.socialnetwork.model.mapper.ProfileMapper;
import org.neonsis.socialnetwork.persistence.repository.CommunityRepository;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
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

    private final ProfileRepository profileRepository;
    private final CommunityRepository communityRepository;

    private final AuthenticationFacade authenticationFacade;

    private final CommunityMapper communityMapper;
    private final ProfileMapper profileMapper;

    @Override
    public CommunityDto findById(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id : " + communityId));

        return toDto(community);
    }

    @Override
    public Page<CommunityDto> findUserFollowCommunities(Long userId, String search, Pageable pageable) {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id : " + userId));

        Page<Community> userCommunities = communityRepository.findUserCommunities(profile, search, pageable);

        return userCommunities.map(this::toDto);
    }

    @Override
    public Page<CommunityDto> findModeratorCommunities(Long moderatorId, String search, Pageable pageable) {
        profileRepository.findById(moderatorId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id : " + moderatorId));

        Page<Community> userCommunities = communityRepository.findModeratorCommunities(moderatorId, search, pageable);

        return userCommunities.map(this::toDto);
    }

    @Override
    public Page<ProfileDto> findCommunityFollowers(Long communityId, Pageable pageable) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id: " + communityId));
        Page<Profile> followers = communityRepository.findCommunityFollowers(community, pageable);

        return followers.map(profileMapper::profileToDto);
    }

    @Override
    public Page<CommunityDto> findCommunities(String search, Pageable pageable) {
        Page<Community> communities = communityRepository.findCommunities(search, pageable);

        return communities.map(this::toDto);
    }

    @Override
    public CommunityDto save(CommunityCreateDto communityCreateDto) {
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + loggedInUserId));

        Community community = Community.builder()
                .moderator(profile)
                .title(communityCreateDto.getTitle())
                .build();

        communityRepository.save(community);

        community.setFollowersCount(0);

        return toDto(community);
    }

    @Override
    public boolean isFollowerOfCommunity(Long communityId) {
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + loggedInUserId));

        return communityRepository.isUserAlreadyJoined(communityId, profile);
    }

    @Override
    public void join(Long communityId) {
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + loggedInUserId));

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id : " + communityId));

        boolean isAlreadyJoined = communityRepository.isUserAlreadyJoined(communityId, profile);

        if (isAlreadyJoined) {
            throw new InvalidWorkFlowException("You're already joined to the community with id: " + communityId);
        }

        boolean isModerator = community.getModerator().getId().equals(profile.getId());

        if (isModerator) {
            throw new InvalidWorkFlowException("Moderator can't joined to the own group");
        }

        community.addFollower(profile);

        communityRepository.save(community);
    }

    @Override
    public void leave(Long communityId) {
        Long loggedInUserId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + loggedInUserId));

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found by id : " + communityId));

        community.removeFollower(profile);

        communityRepository.save(community);
    }

    private CommunityDto toDto(Community community) {
        return communityMapper.communityToDto(community);
    }
}
