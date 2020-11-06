package org.neonsis.socialnetwork.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.rest.payload.request.SignUpRequest;
import org.neonsis.socialnetwork.rest.payload.response.UserAuthResponse;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    UserDto signUpRequestToUserDto(SignUpRequest signUpRequest);

    UserAuthResponse userDtoToUserAuthResponse(UserDto userDto);
}
