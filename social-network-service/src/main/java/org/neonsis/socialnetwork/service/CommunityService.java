package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * {@link Community} service interface.
 *
 * @author neonsis
 */
public interface CommunityService {

    /**
     * Find a community by id.
     *
     * @param communityId the id of the community.
     * @return the founded community.
     */
    CommunityDto findById(Long communityId);

    /**
     * Find communities that a user follows.
     *
     * @param userId   the id of the user.
     * @param pageable paging conditions
     * @return the founded community.
     */
    Page<CommunityDto> findUserFollowCommunities(Long userId, String search, Pageable pageable);

    /**
     * Find communities that a user has created.
     *
     * @param moderatorId the id of the user.
     * @param pageable    paging conditions
     * @return the founded community.
     */
    Page<CommunityDto> findModeratorCommunities(Long moderatorId, String search, Pageable pageable);

    Page<ProfileDto> findCommunityFollowers(Long communityId, Pageable pageable);

    /**
     * Find all communities by title.
     *
     * @param search   the search parameter.
     * @param pageable paging conditions
     * @return the founded community.
     */
    Page<CommunityDto> findCommunities(String search, Pageable pageable);

    /**
     * Create a new community.
     *
     * @param communityCreateDto the community object describing the community to create.
     * @return the founded community.
     */
    CommunityDto save(CommunityCreateDto communityCreateDto);

    boolean isFollowerOfCommunity(Long communityId);

    /**
     * Join to the community.
     *
     * @param communityId the id of the community.
     */
    void join(Long communityId);

    /**
     * Leave the community.
     *
     * @param communityId the id of the community.
     */
    void leave(Long communityId);
}
