package org.neonsis.socialnetwork.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.rest.payload.request.ProfileUpdateRequest;
import org.neonsis.socialnetwork.rest.payload.response.ProfileResponse;

@Mapper(componentModel = "spring")
public interface ProfileRestMapper {

    ProfileResponse profileDtoToProfileResponse(ProfileDto profileDto);

    ProfileDto profileRequestToProfileDto(ProfileUpdateRequest profileUpdateRequest);
}
