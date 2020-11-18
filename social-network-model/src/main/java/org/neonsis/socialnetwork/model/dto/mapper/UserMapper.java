package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.post.UserDto;
import org.neonsis.socialnetwork.model.dto.mapper.impl.PageMapperImpl;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private final PageMapper<UserDto> pageMapper = new PageMapperImpl<>();

    public abstract UserDto userToUserDto(User user);

    public abstract User userDtoToUser(UserDto userDto);

    public PageDto<UserDto> pageUserToPageDtoUserDto(Page<User> userPage) {
        Page<UserDto> map = userPage.map(this::userToUserDto);
        return pageMapper.pageToPageDto(map);
    }
}
