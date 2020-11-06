package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.UserDto;

public interface UserService {

    UserDto signUp(UserDto userDto);

    UserDto findByUuid(String uuid);

    UserDto findByEmail(String email);
}
