package org.neonsis.socialnetwork.rest.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {

    private String uuid;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String email;
    @JsonProperty(value="isFriend")
    private boolean isFriend;
    @JsonProperty(value="isPendingFriendship")
    private boolean isPendingFriendship;
}
