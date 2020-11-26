package org.neonsis.socialnetwork.rest.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.response.*;
import org.neonsis.socialnetwork.service.security.UserPrincipal;

@Mapper(componentModel = "spring")
public interface RestMapper {

    UserAuthResponse userDtoToAuthResponse(UserDto userDto);

    UserResponse userPrincipalToResponse(UserPrincipal userPrincipal);

    UserResponse userDtoToResponse(UserDto userDto);

    CommentResponse commentDtoToResponse(CommentDto commentDto);

    PostResponse postDtoToResponse(PostDto postDto);

    ProfileResponse profileDtoToResponse(ProfileDto profileDto);

    UserDetailsResponse userDtoToDetailsResponse(UserDto userDto);

    MessageResponse messageDtoToResponse(MessageDto messageDto);

    CommunityResponse communityDtoToResponse(CommunityDto communityDto);
}
