package org.neonsis.socialnetwork.rest.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String email;
}
