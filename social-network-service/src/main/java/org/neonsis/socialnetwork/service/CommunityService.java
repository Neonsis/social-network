package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.community.CommunityCreateDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;

public interface CommunityService {

    CommunityDto create(CommunityCreateDto communityCreateDto);
}
