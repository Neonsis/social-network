package org.neonsis.socialnetwork.rest.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.rest.model.response.*;
import org.neonsis.socialnetwork.service.security.UserPrincipal;

@Mapper(componentModel = "spring")
public interface RestMapper {

    UserAuthResponse userDtoToUserAuthResponse(UserDto userDto);

    UserResponse userPrincipalToUserResponse(UserPrincipal userPrincipal);

    CommentResponse commentDtoToCommentResponse(CommentDto commentDto);

    PostResponse postDtoToPostResponse(PostDto postDto);

    PageDto<PostResponse> pageDtoPostDtoToPageDtoPostResponse(PageDto<PostDto> postDtoPageDto);

    ProfileResponse profileDtoToProfileResponse(ProfileDto profileDto);

    UserDetailsResponse userDtoToUserDetailsResponse(UserDto userDto);

    PageDto<UserResponse> pageUserDtoToPageUserResponse(PageDto<UserDto> userDtoPageDto);
}
