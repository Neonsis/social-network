package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.dto.user.ImageDto;

@Getter
@Setter
public class CommunityResponse {

    private String id;

    private String title;

    private UserResponse moderator;

    private Integer followersCount;

    private ImageDto avatar;
}
