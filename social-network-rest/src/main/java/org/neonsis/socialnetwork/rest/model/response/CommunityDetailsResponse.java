package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Image;

@Getter
@Setter
public class CommunityDetailsResponse {

    private String id;

    private String title;

    private UserResponse moderator;

    private Boolean isUserFollow;

    private Image avatar;
}
