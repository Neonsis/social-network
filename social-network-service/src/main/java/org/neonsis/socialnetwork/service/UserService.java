package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.UserDto;

public interface UserService {

    UserDto signUp(UserDto userDto);

    UserDto signIn(UserDto userDto);

    UserDto findByUuid(String uuid);

    UserDto getAuthUser();
}
