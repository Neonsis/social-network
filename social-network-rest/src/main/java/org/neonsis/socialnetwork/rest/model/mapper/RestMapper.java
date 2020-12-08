package org.neonsis.socialnetwork.rest.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.rest.model.response.*;

@Mapper(componentModel = "spring")
public interface RestMapper {

    CommentResponse commentDtoToResponse(CommentDto commentDto);

    PostResponse postDtoToResponse(PostDto postDto);

    ProfileResponse profileDtoToResponse(ProfileDto profileDto);

    ProfileDetailsResponse profileDtoToDetailsResponse(ProfileDto profileDto);

    MessageResponse messageDtoToResponse(MessageDto messageDto);

    CommunityResponse communityDtoToResponse(CommunityDto communityDto);

    CommunityDetailsResponse communityDtoToDetailsResponse(CommunityDto communityDto);

    ProfileUpdateResponse profileDtoToUpdateResponse(ProfileDto profileDto);
}
