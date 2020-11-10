package org.neonsis.socialnetwork.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.rest.payload.response.UserDetailsResponse;
import org.neonsis.socialnetwork.rest.payload.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    UserDetailsResponse userDtoToUserDetailsResponse(UserDto userDto);

    PageDto<UserResponse> pageUserDtoToPageUserResponse(PageDto<UserDto> userDtoPageDto);
}
