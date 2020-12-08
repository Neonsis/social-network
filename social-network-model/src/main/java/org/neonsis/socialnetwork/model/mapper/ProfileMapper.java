package org.neonsis.socialnetwork.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto profileToDto(Profile profile);
}
