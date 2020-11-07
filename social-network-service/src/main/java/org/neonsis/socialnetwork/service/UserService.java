package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.UserDto;

public interface UserService {

    UserDto signUp(UserDto userDto, ProfileDto profileDto);

    UserDto findByUuid(String uuid);

    UserDto findByEmail(String email);
}
