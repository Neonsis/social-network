package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(User user);
}
