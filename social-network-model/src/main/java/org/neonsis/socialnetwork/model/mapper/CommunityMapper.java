package org.neonsis.socialnetwork.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;

@Mapper(componentModel = "spring")
public interface CommunityMapper {

    CommunityDto communityToDto(Community community);
}
