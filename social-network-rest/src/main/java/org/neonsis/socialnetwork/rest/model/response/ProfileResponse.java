package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.dto.user.ImageDto;

@Getter
@Setter
public class ProfileResponse {

    private String id;
    private String firstName;
    private String lastName;
    private ImageDto avatar;
}
