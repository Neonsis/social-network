package org.neonsis.socialnetwork.security.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.security.model.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(User user);
}
